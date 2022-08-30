package com.greymatter.sprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.greymatter.sprint.api.APIClient;
import com.greymatter.sprint.api.APIInterface;
import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.model.response.TSTResponse;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.MyFunction;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bscscan.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);


        Call<TSTResponse> call = apiInterface.getTokenBalance("account","tokenbalance",
                "0x12Aa33065EDf46d41e42945046aAE8A6f5c1622F","0x9C847E81e6F26CBf6140E379cEcCf49484288bAE","latest","H1A6T1YG1RVX6A4MX7RUDCRTJRUCXR7QRY");
        call.enqueue(new Callback<TSTResponse>() {
            @Override
            public void onResponse(Call<TSTResponse> call, Response<TSTResponse> response) {
                MyFunction.cancelLoader();

                TSTResponse body = response.body();
                if (body.getStatus().equals("1")){
                    String balancetsk = body.getResult();
                    balancetsk = balancetsk.substring(0, 5);
                    Toast.makeText(getApplicationContext(), balancetsk + "", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<TSTResponse> call, Throwable t) {
                MyFunction.cancelLoader();
                Toast.makeText(getApplicationContext(), Constant.API_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}