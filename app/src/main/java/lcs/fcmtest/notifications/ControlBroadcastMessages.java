package lcs.fcmtest.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



import lcs.fcmtest.services.BackgroundAppMonitorService;
import lcs.fcmtest.utils.Utils;

import static lcs.fcmtest.services.BackgroundAppMonitorService.controlAcess;


/**
 * Created by lrosa on 31/01/2018.
 */

public class ControlBroadcastMessages extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //|| intent.getAction().equals(Intent.ACTION_SCREEN_ON){


            if (Utils.getIsServiceRunning(context)) {
                Intent mIntent = new Intent(context, BackgroundAppMonitorService.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startForegroundService(mIntent);
            }
        }

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            controlAcess.changeFlagValue(false);
        }
    }
}
