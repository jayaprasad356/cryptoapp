package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityVerifyOtpBinding;
import com.greymatter.sprint.model.response.RegisterResponse;
import com.greymatter.sprint.model.response.SendOtpResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import in.aabhasjindal.otptextview.OTPListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {

    String email,name,password,correct_otp;
    ActivityVerifyOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra(Constant.EMAIL);
        name = getIntent().getStringExtra(Constant.NAME);
        password = getIntent().getStringExtra(Constant.PASSWORD);
        correct_otp = getIntent().getStringExtra(Constant.OTP);

        binding.otpView.requestFocusOTP();
        binding.otpView.requestFocus();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.otpView.getOTP().length() == 6){
                    verifyOtp(binding.otpView.getOTP());
                }else {
                    binding.otpView.showError();
                }
            }
        });

        binding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp();
            }
        });

        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                verifyOtp(otp);
            }
        });

    }

    private void verifyOtp(String otp) {
        if (otp.equals(correct_otp)){
            binding.otpView.showSuccess();
            signup();
            binding.otpView.clearFocus();
        }else {
            binding.otpView.showError();
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void signup() {
        MyFunction.showLoader(VerifyOtpActivity.this);

        Call<RegisterResponse> call = APIClient.getClientWithoutToken().register(
                name,
                email,
                password,
                "Male"
        );
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                MyFunction.cancelLoader();
                RegisterResponse RegisterResponse = response.body();
                if(RegisterResponse.getSuccess()){

                    LocalSession.setLogin(VerifyOtpActivity.this,RegisterResponse);

                    MyFunction.setSharedPrefs(getApplicationContext(), Constant.isLoggedIn,true);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), RegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOtp() {
        MyFunction.showLoader(VerifyOtpActivity.this);

        Call<SendOtpResponse> call = APIClient.getClientWithoutToken().sendOtp(email);
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                MyFunction.cancelLoader();
                SendOtpResponse body = response.body();
                if(body.getSuccess()){
                    correct_otp = body.getOtp()+"";

                }
                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

}