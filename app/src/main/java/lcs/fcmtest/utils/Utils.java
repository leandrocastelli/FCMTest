package lcs.fcmtest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
