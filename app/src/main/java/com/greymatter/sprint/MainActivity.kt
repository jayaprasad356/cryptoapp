package com.greymatter.sprint

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.daniyalak.stepcounterkotlin_androidfitnessapp.service.StepDetectorService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.greymatter.sprint.api.APIClient
import com.greymatter.sprint.callback.StepsCallback
import com.greymatter.sprint.databinding.ActivityMainBinding
import com.greymatter.sprint.fragment.ChallengesActivity
import com.greymatter.sprint.fragment.ProfileActivity
import com.greymatter.sprint.model.response.SaveStepsResponse
import com.greymatter.sprint.model.response.StepsResponse
import com.greymatter.sprint.ui.NotificationActivity
import com.greymatter.sprint.utils.CalorieBurnedCalculator
import com.greymatter.sprint.utils.Constant
import com.greymatter.sprint.utils.MyFunction
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() ,StepsCallback{

    var doubleBackToExitPressedOnce = false
    lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getSensorPermission()
        }

        getCurrentDate()
        
        getStepsListWeekly()
        getStepsListMonthly()

        binding.tokenTxt.setText(MyFunction.getSharedPrefs(applicationContext, Constant.TOKEN, "0") + " Token")
        
        binding.notification.setOnClickListener { view ->
            startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                )
            )
        }

        binding.updateToDb.setOnClickListener { view -> syncTodaySteps() }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    binding.todayContainer.visibility = View.VISIBLE
                    binding.weeklyContainer.visibility = View.GONE
                    binding.monthlyContainer.visibility = View.GONE
                } else if (tab.position == 1) {
                    binding.todayContainer.visibility = View.GONE
                    binding.weeklyContainer.visibility = View.VISIBLE
                    binding.monthlyContainer.visibility = View.GONE
                } else {
                    binding.todayContainer.visibility = View.GONE
                    binding.weeklyContainer.visibility = View.GONE
                    binding.monthlyContainer.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initBottomNav() {
        // Set Home selected
        binding.bottomNavigation.selectedItemId = R.id.home

        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.challenges -> {
                    startActivity(Intent(applicationContext, ChallengesActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home -> return@OnNavigationItemSelectedListener true
                R.id.profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }

    override fun subscribeSteps(steps: Int) {
        MyFunction.setSharedPrefs(applicationContext, Constant.TODAY_STEP_COUNT, steps)

        binding.stepsTaken.setText(steps.toString())

        val weight = MyFunction.getSharedPrefs(applicationContext, Constant.WEIGHT, "50")
        val height = MyFunction.getSharedPrefs(applicationContext, Constant.HEIGHT, "165")

        binding.kcal.text = CalorieBurnedCalculator.getCalorieBurned(weight, height, steps.toString())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getSensorPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACTIVITY_RECOGNITION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    startStepCount()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(applicationContext, "Permission needed for counting steps", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) { /* ... */
                }
            }).check()
    }

    private fun startStepCount() {
        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)

        StepDetectorService.subscribe.register(this)
    }

    private fun getCurrentDate() {
        //Initializing the date formatter
        val Date = DateFormat.getDateInstance()
        //Initializing Calender Object
        val cals = Calendar.getInstance()
        //Using format() method for conversion
        val currentDate = Date.format(cals.time)
        binding.date.text = currentDate
    }

    private fun syncTodaySteps() {
        val step_count = MyFunction.getSharedPrefs(applicationContext, Constant.TODAY_STEP_COUNT, 0)

        if (step_count > 0) {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()
            val current_date = formatter.format(date)
            MyFunction.showLoader(this)
            val call = APIClient.getClientWithoutToken().saveSteps(
                MyFunction.getSharedPrefs(applicationContext, Constant.USER_ID, ""),
                step_count.toString(),
                binding.kcal.text.toString(),
                current_date
            )
            call.enqueue(object : Callback<SaveStepsResponse?> {
                override fun onResponse(
                    call: Call<SaveStepsResponse?>,
                    response: Response<SaveStepsResponse?>
                ) {
                    MyFunction.cancelLoader()
                    if (response.isSuccessful) {
                        val body = response.body()
                        Toast.makeText(applicationContext, body!!.message, Toast.LENGTH_SHORT).show()
                        if (body.success) {
                        }
                    } else {
                        Toast.makeText(applicationContext, Constant.API_ERROR, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SaveStepsResponse?>, t: Throwable) {
                    MyFunction.cancelLoader()
                    Toast.makeText(applicationContext, Constant.API_ERROR, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(applicationContext, "Step count must be greater than 0", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getStepsListWeekly() {
        val call = APIClient.getClientWithoutToken().getSteps(
            MyFunction.getSharedPrefs(applicationContext, Constant.USER_ID, ""),
            Constant.WEEKLY
        )
        call.enqueue(object : Callback<StepsResponse?> {
            override fun onResponse(
                call: Call<StepsResponse?>,
                response: Response<StepsResponse?>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body!!.isSuccess) {
                        binding.stepsTakenWeekly.text = body.steps
                        binding.kcalWeekly.text = body.calories
                    }
                }
            }

            override fun onFailure(call: Call<StepsResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, Constant.API_ERROR, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getStepsListMonthly() {
        val call = APIClient.getClientWithoutToken().getSteps(
            MyFunction.getSharedPrefs(applicationContext, Constant.USER_ID, ""),
            Constant.MONTHLY
        )
        call.enqueue(object : Callback<StepsResponse?> {
            override fun onResponse(
                call: Call<StepsResponse?>,
                response: Response<StepsResponse?>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body!!.isSuccess) {
                        binding.stepsTakenMonthly.text = body.steps
                        binding.kcalMonthly.text = body.calories
                    }
                }
            }

            override fun onFailure(call: Call<StepsResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, Constant.API_ERROR, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.username.text =
            "Welcome, " + MyFunction.getSharedPrefs(applicationContext, Constant.NAME, "Guest")
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //***Change Here***
            startActivity(intent)
            finish()
            System.exit(0)
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}