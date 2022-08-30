package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.greymatter.sprint.BuildConfig;
import com.greymatter.sprint.R;
import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.model.response.ProfileResponse;
import com.greymatter.sprint.model.response.SettingsResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkUpdate();
            }
        }, Constant.SPLASH_DURATION);




    }

    private void checkUpdate()
    {
        Call<SettingsResponse> call = APIClient.getClientWithoutToken().getSettings("1");
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                MyFunction.cancelLoader();
                SettingsResponse settingsResponse = response.body();
                if(settingsResponse.getSuccess()){
                    int versionCode = BuildConfig.VERSION_CODE;
                    if (settingsResponse.getData().get(0).getApp_version().equals(""+versionCode)){
                        redirectApp();
                    }
                    else {
                        if (settingsResponse.getData().get(0).getUpdate_type().equals("force")){
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setTitle("New Update Availble ! ")
                                    .setMessage(settingsResponse.getData().get(0).getApp_description())
                                    .setCancelable(false)
                                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(settingsResponse.getData().get(0).getApp_link()));
                                            startActivity(i);
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }else {
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setTitle("New Update Availble ! ")
                                    .setMessage(settingsResponse.getData().get(0).getApp_description())
                                    .setCancelable(false)
                                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(settingsResponse.getData().get(0).getApp_link()));
                                            startActivity(i);
                                        }
                                    })
                                    .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            redirectApp();


                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }


                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), settingsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectApp() {
        if (MyFunction.getSharedPrefs(getApplicationContext(),Constant.isLoggedIn,false)){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        }
    }
}