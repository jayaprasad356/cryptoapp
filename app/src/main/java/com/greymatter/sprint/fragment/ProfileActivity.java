package com.greymatter.sprint.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daniyalak.stepcounterkotlin_androidfitnessapp.service.StepDetectorService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.R;
import com.greymatter.sprint.databinding.ActivityProfile2Binding;
import com.greymatter.sprint.ui.ChangePasswordActivity;
import com.greymatter.sprint.ui.NotificationActivity;
import com.greymatter.sprint.ui.SplashActivity;
import com.greymatter.sprint.ui.StepHistoryActivity;
import com.greymatter.sprint.ui.WalletActivity;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.MyFunction;

public class ProfileActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    ActivityProfile2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfile2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomNav();

        binding.editProfile.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), com.greymatter.sprint.ui.ProfileActivity.class)));

        binding.wallet.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), WalletActivity.class)));

        binding.stepHistory.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), StepHistoryActivity.class)));

        binding.changePassword.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class)));
        binding.notification.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class)));

        binding.telegram1.setOnClickListener(view ->
                openUrl(Constant.TELEGRAM1));
        binding.telegram2.setOnClickListener(view ->
                openUrl(Constant.TELEGRAM2));
        binding.web.setOnClickListener(view ->
                openUrl(Constant.WEB));
        binding.twitter.setOnClickListener(view ->
                openUrl(Constant.TWITTER));

        binding.logout.setOnClickListener(view -> logout());

    }

    private void openUrl(String url) {
        final Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    private void initBottomNav() {
        // Set Profile selected
        binding.bottomNavigation.setSelectedItemId(R.id.profile);

        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(),ChallengesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

    }

    private void logout() {
        stopService(new Intent(getApplicationContext(), StepDetectorService.class));
        MyFunction.logout(getApplicationContext());
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileDetails();
    }

    private void setProfileDetails() {
        binding.username.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.NAME,"Guest"));
        binding.weight.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.WEIGHT,"50")+" kg");
        binding.height.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.HEIGHT,"165")+" cm");
        binding.age.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.AGE,"20")+" years");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}