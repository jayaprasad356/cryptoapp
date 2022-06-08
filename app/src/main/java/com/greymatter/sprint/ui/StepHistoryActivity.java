package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.StepHistoryAdapter;
import com.greymatter.sprint.databinding.ActivityStepHistoryBinding;
import com.greymatter.sprint.utils.ConstantList;

public class StepHistoryActivity extends AppCompatActivity {

    ActivityStepHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStepHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.stepRecycler.setAdapter(new StepHistoryAdapter(StepHistoryActivity.this, ConstantList.getStepList()));
    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("History");
    }

}