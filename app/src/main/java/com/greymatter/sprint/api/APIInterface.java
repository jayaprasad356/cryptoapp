package com.greymatter.sprint.api;

import com.greymatter.sprint.model.StepHistory;
import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.model.response.ProfileResponse;
import com.greymatter.sprint.model.response.RegisterResponse;
import com.greymatter.sprint.model.response.SaveStepsResponse;
import com.greymatter.sprint.model.response.SendOtpResponse;
import com.greymatter.sprint.model.response.StepHistoryResponse;
import com.greymatter.sprint.model.response.StepsResponse;
import com.greymatter.sprint.utils.Constant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("minbal.php")
    Call<LoginResponse> minbal(
            @Field(Constant.BALANCE) String balance);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field(Constant.EMAIL) String email,
            @Field(Constant.PASSWORD) String password);
    @FormUrlEncoded
    @POST("forgotpassword.php")
    Call<LoginResponse> forgot(
            @Field(Constant.EMAIL) String email);

    @FormUrlEncoded
    @POST("signup.php")
    Call<RegisterResponse> register(
            @Field(Constant.NAME) String name,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.PASSWORD) String password,
            @Field(Constant.GENDER) String gender);

    @FormUrlEncoded
    @POST("verifyemail.php")
    Call<SendOtpResponse> sendOtp(@Field(Constant.EMAIL) String email);

    @FormUrlEncoded
    @POST("changepassword.php")
    Call<LoginResponse> changePassword(
            @Field(Constant.USER_ID) String user_id,
            @Field(Constant.PASSWORD) String password,
            @Field(Constant.OLDPASSWORD) String oldpassword
    );

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<ProfileResponse> updateProfile(
            @Field(Constant.USER_ID) String user_id,
            @Field(Constant.NAME) String name,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.WEIGHT) String weight,
            @Field(Constant.HEIGHT) String height,
            @Field(Constant.AGE) String age);

    @FormUrlEncoded
    @POST("savesteps.php")
    Call<SaveStepsResponse> saveSteps(
            @Field(Constant.USER_ID) String user_id,
            @Field(Constant.TODAY_STEP_COUNT) String today_step_count,
            @Field(Constant.CALORIES) String calories,
            @Field(Constant.DATE) String date);

    @FormUrlEncoded
    @POST("stepslist.php")
    Call<StepsResponse> getSteps(
            @Field(Constant.USER_ID) String user_id,
            @Field(Constant.TYPE) String type
            );

    @FormUrlEncoded
    @POST("history.php")
    Call<StepHistoryResponse> getStepHistory(
            @Field(Constant.MONTH) String month);


}