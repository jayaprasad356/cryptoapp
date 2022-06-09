package com.greymatter.sprint.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.StepHistoryAdapter;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityStepHistoryBinding;
import com.greymatter.sprint.model.response.StepHistoryResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepHistoryActivity extends AppCompatActivity {

    String month="1";
    ActivityStepHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStepHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month = String.valueOf(i+1);
                getStepHistory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //getStepHistory();
    }

    private void getStepHistory() {
        MyFunction.showLoader(StepHistoryActivity.this);

        Call<StepHistoryResponse> call = APIClient.getClientWithoutToken().getStepHistory(
                month
        );
        call.enqueue(new Callback<StepHistoryResponse>() {
            @Override
            public void onResponse(Call<StepHistoryResponse> call, Response<StepHistoryResponse> response) {
                MyFunction.cancelLoader();

                if (response.isSuccessful()){
                    StepHistoryResponse body = response.body();
                    if(body.getSuccess()){

                        binding.stepRecycler.setAdapter(new StepHistoryAdapter(StepHistoryActivity.this, body.getData()));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StepHistoryResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("History");
    }

}