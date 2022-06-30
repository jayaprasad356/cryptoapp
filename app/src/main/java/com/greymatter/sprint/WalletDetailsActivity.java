package com.greymatter.sprint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.ui.ChangePasswordActivity;
import com.greymatter.sprint.ui.SigninActivity;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletDetailsActivity extends AppCompatActivity {
    Button btnLogin;
    Activity activity;
    String Balance;
    Session session;
    TextView tvBalance,tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);
        btnLogin = findViewById(R.id.btnLogin);
        tvBalance = findViewById(R.id.tvBalance);
        tvAddress = findViewById(R.id.tvAddress);
        activity = WalletDetailsActivity.this;
        session = new Session(WalletDetailsActivity.this);
        session.setBoolean("walletconnect",true);
        Balance = session.getData(Constant.BALANCE);
        tvAddress.setText(session.getData(Constant.ADDRESS));
        tvBalance.setText(Balance);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.showLoader(activity);

                Call<LoginResponse> call = APIClient.getClientWithoutToken().minbal(
                        Balance
                );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        MyFunction.cancelLoader();
                        LoginResponse loginResponse = response.body();
                        if(loginResponse.getSuccess()){

                            MyFunction.setSharedPrefs(getApplicationContext(), Constant.isLoggedIn,true);

                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        MyFunction.cancelLoader();
                        Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWallet();
    }

    private void updateWallet() {
        MyFunction.showLoader(WalletDetailsActivity.this);

        Call<LoginResponse> call = APIClient.getClientWithoutToken().updateWallet(
                MyFunction.getSharedPrefs(getApplicationContext(),Constant.USER_ID,""),
                session.getData(Constant.BALANCE),session.getData(Constant.ADDRESS)
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyFunction.cancelLoader();

                LoginResponse body = response.body();
                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}