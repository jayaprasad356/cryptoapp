package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.R;
import com.greymatter.sprint.WalletConnectActivity;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.model.response.RegisterResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.LocalSession;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGenderActivity extends AppCompatActivity {
    RelativeLayout male,female;
    Button submit;
    boolean maleexist = false,femaleexist = false;
    String gender = "";
    String email,name,password;
    EditText weight,height,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        email = getIntent().getStringExtra(Constant.EMAIL);
        name = getIntent().getStringExtra(Constant.NAME);
        password = getIntent().getStringExtra(Constant.PASSWORD);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        submit = findViewById(R.id.submit);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        age = findViewById(R.id.age);
        gender = "Male";
        maleexist = true;
        femaleexist = false;
        male.setBackgroundResource(R.drawable.voilet_fill_curve_stroke_bg);
        female.setBackgroundResource(R.drawable.voilet_curve_stroke_bg);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!maleexist){
                    gender = "Male";
                    maleexist = true;
                    femaleexist = false;
                    male.setBackgroundResource(R.drawable.voilet_fill_curve_stroke_bg);
                    female.setBackgroundResource(R.drawable.voilet_curve_stroke_bg);
                }


            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!femaleexist){
                    gender = "Female";
                    femaleexist = true;
                    maleexist = false;
                    male.setBackgroundResource(R.drawable.voilet_curve_stroke_bg);
                    female.setBackgroundResource(R.drawable.voilet_fill_curve_stroke_bg);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weight.getText().toString().trim().equals("")){
                    weight.setError("Enter Weight");
                    weight.requestFocus();
                }
                else if (height.getText().toString().trim().equals("")){
                    height.setError("Enter Height");
                    height.requestFocus();
                }
                else if (age.getText().toString().trim().equals("")){
                    age.setError("Enter Age");
                    age.requestFocus();
                }
                else {
                    signup();

                }



            }
        });

    }
    private void signup() {
        MyFunction.showLoader(SelectGenderActivity.this);

        Call<RegisterResponse> call = APIClient.getClientWithoutToken().register(
                name,
                email,
                password,
                gender,
                weight.getText().toString().trim(),
                height.getText().toString().trim(),
                age.getText().toString().trim()
        );
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                MyFunction.cancelLoader();
                RegisterResponse RegisterResponse = response.body();
                if(RegisterResponse.getSuccess()){

                    LocalSession.setLogin(SelectGenderActivity.this,RegisterResponse);

                    startActivity(new Intent(getApplicationContext(), WalletConnectActivity.class));
                    finish();

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
}