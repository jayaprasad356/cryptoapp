package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.sprint.R;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityProfileBinding;
import com.greymatter.sprint.model.response.ProfileResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.name.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.NAME,"Guest"));
        binding.email.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.EMAIL,"Guest"));
        binding.weight.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.WEIGHT,"50"));
        binding.height.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.HEIGHT,"165"));
        binding.age.setText(MyFunction.getSharedPrefs(getApplicationContext(), Constant.AGE,"20"));

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()) {
                    updateProfile();
                }
            }
        });


    }

    private void updateProfile() {
        MyFunction.showLoader(ProfileActivity.this);

        Call<ProfileResponse> call = APIClient.getClientWithoutToken().updateProfile(
                MyFunction.getSharedPrefs(getApplicationContext(),Constant.USER_ID,""),
                binding.name.getText().toString().trim(),
                MyFunction.getSharedPrefs(getApplicationContext(),Constant.EMAIL,""),
                binding.weight.getText().toString().trim(),
                binding.height.getText().toString().trim(),
                binding.age.getText().toString().trim()
        );
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                MyFunction.cancelLoader();
                ProfileResponse ProfileResponse = response.body();
                if(ProfileResponse.getSuccess()){

                    LocalSession.setProfile(ProfileActivity.this,ProfileResponse);

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), ProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValid() {
        if (MyFunction.isEmpty(binding.name)) return false;
        if (MyFunction.isEmpty(binding.weight)) return false;
        if (MyFunction.isEmpty(binding.height)) return false;
        if (MyFunction.isEmpty(binding.age)) return false;

        return true;
    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Profile");
    }

}