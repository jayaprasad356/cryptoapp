package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivitySigninBinding;
import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()) {
                    signin();
                }
            }
        });

        binding.signup.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SignupActivity.class))
                );
        binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyFunction.isEmpty(binding.email)){
                    binding.email.setError("Enter Email");
                    binding.email.requestFocus();
                }else {
                    forgotPassword();

                }
            }
        });
    }

    private void forgotPassword() {
        MyFunction.showLoader(SigninActivity.this);

        Call<LoginResponse> call = APIClient.getClientWithoutToken().forgot(
                binding.email.getText().toString().trim()
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyFunction.cancelLoader();
                LoginResponse loginResponse = response.body();
                if(loginResponse.getSuccess()){

                    Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signin() {
        MyFunction.showLoader(SigninActivity.this);

        Call<LoginResponse> call = APIClient.getClientWithoutToken().login(
                binding.email.getText().toString().trim(),
                binding.password.getText().toString().trim()
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyFunction.cancelLoader();
                LoginResponse loginResponse = response.body();
                if(loginResponse.getSuccess()){

                    LocalSession.setLogin(SigninActivity.this,loginResponse);

                    MyFunction.setSharedPrefs(getApplicationContext(), Constant.isLoggedIn,true);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean isValid() {
        if (MyFunction.isEmpty(binding.email)) return false;
        if (MyFunction.isEmpty(binding.password)) return false;
        if (!MyFunction.isValidEmail(binding.email)) return false;

        return true;
    }
}