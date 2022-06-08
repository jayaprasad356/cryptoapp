package com.greymatter.sprint.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MyFunction {

    static String app_name = "Sprint";
    private static ProgressDialog progressDialog;

    /**
     * Sets shared prefs.
     *
     * @param c     the c
     * @param key   the key
     * @param value the value
     */
    public static void setSharedPrefs(Context c, String key, Boolean value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setSharedPrefs(Context c, String key, String value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPrefs(Context c, String key, int value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setSharedPrefs(Context c, String key, float value) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Gets shared prefs.
     *
     * @param c             the c
     * @param key           the key
     * @param default_value the default value
     * @return the shared prefs
     */
    public static Boolean getSharedPrefs(Context c, String key, Boolean default_value) {
        SharedPreferences prefs = c.getSharedPreferences(app_name, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, default_value);
    }

    public static String getSharedPrefs(Context c, String key, String default_value) {
        SharedPreferences prefs = c.getSharedPreferences(app_name, Context.MODE_PRIVATE);
        return prefs.getString(key, default_value);
    }

    public static int getSharedPrefs(Context c, String key, int default_value) {
        if (c == null) {
            return default_value;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.getInt(key, default_value);
        }
    }

    public static float getSharedPrefs(Context c, String key, float default_value) {
        if (c == null) {
            return default_value;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.getFloat(key, default_value);
        }
    }

    /*Delete all local values*/
    public static void logout(Context c) {
        SharedPreferences.Editor prefs = c.getSharedPreferences(app_name, Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.commit();
    }

    public static String ConvertDate(String old_date, String old_format, String new_format) {
        SimpleDateFormat sdf = new SimpleDateFormat(old_format, Locale.ENGLISH);
        Date d;
        try {
            d = sdf.parse(old_date);
            sdf.applyPattern(new_format);
            return sdf.format(d);

        } catch (ParseException e) {
            e.printStackTrace();
            return old_date;
        }
    }

    public static Long getTimemilliUTC(String value) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //input.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = null;
        try {
            d = input.parse(value);
        } catch (android.net.ParseException | ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(app_name, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(app_name, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(app_name, "printHashKey()", e);
        }
    }

    //Edittext Empty validation
    public static boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()){
            editText.setError(Constant.EMPTY_ERROR);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    //Textview Empty validation
    public static boolean isEmpty(TextView textView) {
        if (textView.getText().toString().trim().isEmpty()){
            textView.setError(Constant.EMPTY_ERROR);
            textView.requestFocus();
            return true;
        }
        return false;
    }

    //Edittext mobile number validation
    public static boolean isValidMobileNumber(EditText editText) {
        if (editText.getText().toString().trim().length() != 10 || Long.parseLong(editText.getText().toString().trim()) == 0){
            editText.setError("Please fill valid phone number");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidPincode(EditText editText) {
        if (editText.getText().toString().trim().length() != 6){
            editText.setError("Please fill valid pincode number");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(EditText editText) {
        if (editText.getText().toString().trim().length() < 8){
            editText.setError("Password must be minimum 8 characters");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    //Edittext Email validation
    public static boolean isValidEmail(EditText editText) {
        String email = editText.getText().toString().trim();
        boolean valid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!valid){
            editText.setError("Please fill valid email");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    //speech to text
    public static void promptSpeechInput(Activity context, int req_code_speech_input) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Prompt");
        try {
            context.startActivityForResult(intent, req_code_speech_input);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context, "Speech not supported", Toast.LENGTH_SHORT).show();
        }
    }

    public static void checkDarkMode(Context context) {
        if (MyFunction.getSharedPrefs(context,Constant.isDarkModeOn,false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void requestPermissionsCompat(String[] permissions, int requestCode, Activity context) {
        ActivityCompat.requestPermissions(context, permissions, requestCode);
    }

    public static boolean arePermissionsGranted(String[] permissions,Activity context) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;

        }
        return true;
    }

    public static void showLoader(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public static void cancelLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void toastShort(Context c, String msg) {
        if (c != null && msg != null)
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context c, String msg) {
        if (c != null && msg != null)
            Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
    public static void serverError(Context c) {
        if (c != null)
            Toast.makeText(c, "Something went wrong, please try again later", Toast.LENGTH_LONG).show();
    }

    public static void BASE64_DECODE(ImageView imageView, String base64_string) {
        byte[] bytes=Base64.decode(base64_string, Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        // set bitmap on imageView
        imageView.setImageBitmap(bitmap);
    }

    public static String getUniqueID(Context c) {
        String myAndroidDeviceId = "";
        /*TelephonyManager mTelephony = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
		if (mTelephony.getDeviceId() != null) {
			myAndroidDeviceId = mTelephony.getDeviceId();
		} else {
			myAndroidDeviceId = Secure.getString(c.getContentResolver(),
					Secure.ANDROID_ID);
		}*/
        myAndroidDeviceId = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
        return myAndroidDeviceId;
    }

    public static String convertToINR(String price) {
        /*NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(Constants.INR));
        double amount = Double.parseDouble(price);*/
        Locale locale = new Locale("en","IN");
        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        dfs.setCurrencySymbol("\u20B9");
        format.setDecimalFormatSymbols(dfs);
        double amount = Double.parseDouble(price);
        return format.format(amount);
    }

    public static String setReviewCount(String reviews_count) {
        int count = Integer.parseInt(reviews_count);
        if (count == 1 ) return reviews_count+" Review";
        if (count > 1 ) return reviews_count+" Reviews";
        else return "0 Review";
    }
    public static String setReviewCountInt(int count) {
        if (count == 1 ) return count+" Review";
        if (count > 1 ) return count+" Reviews";
        else return "0 Review";
    }
}
