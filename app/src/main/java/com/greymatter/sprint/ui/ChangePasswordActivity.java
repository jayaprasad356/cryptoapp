package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.sprint.R;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityChangePasswordBinding;
import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.model.response.SendOtpResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    changePassword();
                }
            }
        });

    }

    private boolean isValid() {
        if (MyFunction.isEmpty(binding.oldPassword)) return false;
        if (MyFunction.isEmpty(binding.password)) return false;
        if (MyFunction.isEmpty(binding.confirmPassword)) return false;
        if (!binding.password.getText().toString().trim().equals(binding.confirmPassword.getText().toString().trim())){
            binding.confirmPassword.setError("Password doesn't match");
            binding.confirmPassword.requestFocus();
            return false;
        }

        return true;
    }


    private void changePassword() {
        MyFunction.showLoader(ChangePasswordActivity.this);

        Call<LoginResponse> call = APIClient.getClientWithoutToken().changePassword(
                MyFunction.getSharedPrefs(getApplicationContext(),Constant.USER_ID,""),
                binding.password.getText().toString().trim(),binding.oldPassword.getText().toString().trim()
                );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyFunction.cancelLoader();

                LoginResponse body = response.body();
                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                if(body.getSuccess()){
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

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Change Password");
    }

}