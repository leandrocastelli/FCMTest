package lcs.fcmtest.utils;

import android.util.Log;

/**
 * Created by lrosa on 02/02/2018.
 */

public class AcessControl {
    private static boolean isLoggedIn = false;
    private static Object mPauseLock;

    public AcessControl() {

        mPauseLock = new Object();
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void changeFlagValue(boolean flagValue) {

        synchronized (mPauseLock) {
            boolean newValue = flagValue;
            isLoggedIn = newValue;

        }
    }

}
