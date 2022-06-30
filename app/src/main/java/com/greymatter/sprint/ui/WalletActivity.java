package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.sprint.R;
import com.greymatter.sprint.adapter.StepHistoryAdapter;
import com.greymatter.sprint.adapter.WalletHistoryAdapter;
import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.databinding.ActivityWalletBinding;
import com.greymatter.sprint.model.response.StepHistoryResponse;
import com.greymatter.sprint.model.response.WalletHistoryResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.ConstantList;
import com.greymatter.sprint.utils.MyFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        getWalletHistory();



    }

    private void getWalletHistory()
    {
        MyFunction.showLoader(WalletActivity.this);

        Call<WalletHistoryResponse> call = APIClient.getClientWithoutToken().getWalletHistory(
                MyFunction.getSharedPrefs(getApplicationContext(),Constant.USER_ID,"")
        );
        call.enqueue(new Callback<WalletHistoryResponse>() {
            @Override
            public void onResponse(Call<WalletHistoryResponse> call, Response<WalletHistoryResponse> response) {
                MyFunction.cancelLoader();

                if (response.isSuccessful()){
                    WalletHistoryResponse body = response.body();

                    if(body.getSuccess()){

                        binding.walletRecycler.setAdapter(new WalletHistoryAdapter(WalletActivity.this, body.getData()));
                    }
                    else{
                        binding.walletRecycler.setAdapter(new WalletHistoryAdapter(WalletActivity.this, body.getData()));
                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<WalletHistoryResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initToolbar() {
        findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Transaction");
    }
}