package lcs.fcmtest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Properties;

public class Utils {

    public static String getRolePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.ROLE_PREFERENCE, "");
    }

    public static void setRolePreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.ROLE_PREFERENCE, value);
        sharedPreferences.apply();

    }
    public static String getTokenPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.TOKEN_PREFERENCE, "");
    }

    public static void setTokenPreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.TOKEN_PREFERENCE, value);
        sharedPreferences.apply();

    }
    public static String getUserPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.USER_PREFERENCE, "");
    }

    public static void setUserPreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.USER_PREFERENCE, value);
        sharedPreferences.apply();

    }
    public static String convertPackageNameToPath(String packageName) {
        return packageName.replace('.','@');
    }
    public static String convertPathToPackageName(String path) {
        return path.replace('@','.');
    }

    public static boolean getIsServiceRunning(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("service",false);
    }
    public static void setIsServiceRunning(Context context, boolean value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putBoolean("service", value);
        sharedPreferences.apply();

    }

    public static String getParentPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.PARENT_PREFERENCE, "");
    }

    public static void setParentPreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.PARENT_PREFERENCE, value);
        sharedPreferences.apply();

    }

    public static String getNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.NAME_DATA_KEY, "");
    }

    public static void setNamePreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.NAME_DATA_KEY, value);
        sharedPreferences.apply();

    }

    public static String getEmailPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.EMAIL_DATA_KEY, "");
    }

    public static void setEmailPreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.EMAIL_DATA_KEY, value);
        sharedPreferences.apply();

    }

    public static boolean isMoto() {
        Properties sysProps = System.getProperties();
        String userAgent = sysProps.getProperty("http.agent","");
        return  userAgent.contains("moto");
    }


}
