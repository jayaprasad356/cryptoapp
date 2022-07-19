package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.NotificationAdapter;
import com.greymatter.sprint.adapter.StepHistoryAdapter;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityNotificationBinding;
import com.greymatter.sprint.model.Notification;
import com.greymatter.sprint.model.response.NotificationResponse;
import com.greymatter.sprint.model.response.StepHistoryResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.ConstantList;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        getNotifyList();

    }

    private void getNotifyList()
    {
        MyFunction.showLoader(NotificationActivity.this);
        Call<NotificationResponse> call = APIClient.getClientWithoutToken().getNotifications(
                "1"
        );
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                MyFunction.cancelLoader();

                if (response.isSuccessful()){
                    NotificationResponse body = response.body();

                    if(body.getSuccess()){

                        binding.notificationRecycler.setAdapter(new NotificationAdapter(NotificationActivity.this, body.getData()));
                    }
                    else{
                        binding.notificationRecycler.setAdapter(new NotificationAdapter(NotificationActivity.this, body.getData()));
                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Notification");
    }

}