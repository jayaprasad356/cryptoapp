package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivitySignupBinding;
import com.greymatter.sprint.model.response.RegisterResponse;
import com.greymatter.sprint.model.response.SendOtpResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    String otp;
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()) {
                    sendOtp();
                }
            }
        });

        binding.signin.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SigninActivity.class))
        );
    }

    private void sendOtp() {
        MyFunction.showLoader(SignupActivity.this);

        Call<SendOtpResponse> call = APIClient.getClientWithoutToken().sendOtp(binding.email.getText().toString().trim());
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                MyFunction.cancelLoader();
                SendOtpResponse body = response.body();
                if(body.getSuccess()){
                    otp = body.getOtp()+"";
                    signup();
                }
                else{
                    Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signup() {

        startActivity(new Intent(getApplicationContext(), VerifyOtpActivity.class)
                .putExtra(Constant.EMAIL,binding.email.getText().toString().trim())
                .putExtra(Constant.NAME,binding.name.getText().toString().trim())
                .putExtra(Constant.PASSWORD,binding.password.getText().toString().trim())
                .putExtra(Constant.OTP,otp)
        );
    }

    private boolean isValid() {
        if (MyFunction.isEmpty(binding.name)) return false;
        if (MyFunction.isEmpty(binding.email)) return false;
        if (MyFunction.isEmpty(binding.password)) return false;
        if (MyFunction.isEmpty(binding.confirmPassword)) return false;
        if (!MyFunction.isValidEmail(binding.email)) return false;
        if (!binding.password.getText().toString().trim().equals(binding.confirmPassword.getText().toString().trim())){
            binding.confirmPassword.setError("Password doesn't match");
            binding.confirmPassword.requestFocus();
            return false;
        }

        return true;
    }

}