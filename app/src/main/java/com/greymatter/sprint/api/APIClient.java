package com.greymatter.sprint.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greymatter.sprint.utils.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static APIInterface getClientWithoutToken() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .writeTimeout(100, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        clientBuilder = clientBuilder.addInterceptor(interceptor);

        Retrofit retrofit = builder
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))//add okHttp client
                .client(clientBuilder.build())
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getClient(final String token) {

        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("authorization:", token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit.create(APIInterface.class);
    }
}
