package com.greymatter.sprint.utils;

import java.util.Locale;

public class Constant {

    String TAG = Constant.class.getSimpleName();
    public static int SPLASH_DURATION = 3000;
    public static String BASE_URL = "http://crypto.greymatterworks.in/api/";
    //public static String BASE_URL = "http://192.168.43.38/cryptoapp/api/";
    public static String API_ERROR = "Something went wrong, please try again later!";
    public static final String EMPTY_ERROR = "Please fill in this field";
    public static final String isLoggedIn = "isLoggedIn";
    public static String isDarkModeOn = "isDarkModeOn";
    public static String BALANCE = "balance";
    public static String Rupees = "₹";
    public static final String SAVED_STEPS = "saved_steps";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String TODAY_STEP_COUNT = "today_step_count";
    public static int DEFAULT_GOAL = 100;
    public static double DEFAULT_TOKEN_VALUE = 0.01;
    public static float DEFAULT_STEP_SIZE = Locale.getDefault() == Locale.US ? 2.5f : 75f;
    public static String DEFAULT_STEP_UNIT = Locale.getDefault() == Locale.US ? "ft" : "cm";
    public static final String TOKEN = "token";
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String OLDPASSWORD = "oldpassword";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String CALORIES = "calories";
    public static final String DATE = "date";
    public static final String TYPE = "type";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";
    public static final String OTP = "otp";
    public static final String INSTAGRAM = "http://crypto.greymatterworks.in";
    public static final String TELEGRAM = "http://crypto.greymatterworks.in";
    public static final String WHATSAPP = "http://crypto.greymatterworks.in";
    public static final String MONTH = "month";
}
