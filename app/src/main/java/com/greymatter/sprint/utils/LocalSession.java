package com.greymatter.sprint.utils;

import android.content.Context;

import com.greymatter.sprint.model.response.LoginResponse;
import com.greymatter.sprint.model.response.ProfileResponse;
import com.greymatter.sprint.model.response.RegisterResponse;
import com.greymatter.sprint.ui.ProfileActivity;

public class LocalSession {

    public static void setLogin(Context context, LoginResponse response) {
        MyFunction.setSharedPrefs(context, Constant.USER_ID,response.getData().get(0).getId());
        MyFunction.setSharedPrefs(context, Constant.EMAIL,response.getData().get(0).getEmail());
        MyFunction.setSharedPrefs(context, Constant.NAME,response.getData().get(0).getName());
        MyFunction.setSharedPrefs(context, Constant.REWARD,response.getData().get(0).getReward());
    }
    public static void setLogin(Context context, RegisterResponse response) {
        MyFunction.setSharedPrefs(context, Constant.USER_ID,response.getData().get(0).getId());
        MyFunction.setSharedPrefs(context, Constant.EMAIL,response.getData().get(0).getEmail());
        MyFunction.setSharedPrefs(context, Constant.NAME,response.getData().get(0).getName());
        MyFunction.setSharedPrefs(context, Constant.NAME,response.getData().get(0).getName());
    }

    public static void setProfile(Context context, ProfileResponse response) {
        MyFunction.setSharedPrefs(context, Constant.NAME,response.getData().get(0).getName());
        MyFunction.setSharedPrefs(context, Constant.WEIGHT,response.getData().get(0).getWeight());
        MyFunction.setSharedPrefs(context, Constant.HEIGHT,response.getData().get(0).getHeight());
        MyFunction.setSharedPrefs(context, Constant.AGE,response.getData().get(0).getAge());
    }
}
