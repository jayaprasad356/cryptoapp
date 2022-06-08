package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.WalletHistoryAdapter;
import com.greymatter.sprint.databinding.ActivityWalletBinding;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.ConstantList;
import com.greymatter.sprint.utils.MyFunction;

public class WalletActivity extends AppCompatActivity {

    String token;
    ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        token = MyFunction.getSharedPrefs(getApplicationContext(), Constant.TOKEN,"0");
        binding.tokenTxt.setText(token+" Token");

        calculateINR();


        binding.walletRecycler.setAdapter(new WalletHistoryAdapter(WalletActivity.this, ConstantList.getWalletList()));
    }

    private void calculateINR() {

        double token_value = Constant.DEFAULT_TOKEN_VALUE;
        double inr = token_value * Double.parseDouble(token);
        binding.amount.setText(MyFunction.convertToINR(inr+""));
    }

    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Wallet");
    }
}