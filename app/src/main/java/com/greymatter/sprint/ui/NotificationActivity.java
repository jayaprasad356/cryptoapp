package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.NotificationAdapter;
import com.greymatter.sprint.databinding.ActivityNotificationBinding;
import com.greymatter.sprint.utils.ConstantList;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.notificationRecycler.setAdapter(new NotificationAdapter(NotificationActivity.this, ConstantList.getNotificationList()));
    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Notification");
    }

}