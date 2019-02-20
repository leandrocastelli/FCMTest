package lcs.fcmtest.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import lcs.fcmtest.notifications.ControlBroadcastMessages;
import lcs.fcmtest.utils.AcessControl;
import lcs.fcmtest.utils.Utils;

/**
 * Created by lrosa on 31/01/2018.
 */

public class BackgroundAppMonitorService extends Service {
    private Context context;
    private BroadcastReceiver mReceiver;
    private static GetForegroundAppMonitor task;
    public static boolean destroyService;


    public static AcessControl controlAcess = new AcessControl();

    public BackgroundAppMonitorService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        destroyService = false;
        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ControlBroadcastMessages();
        registerReceiver(mReceiver, filter);
        // startForeground();
        //startForeground(1,new Notification());

        task = new GetForegroundAppMonitor(context);
        task.execute();

        return START_STICKY;

    }

/*
## Set notification for apk

    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText("Running")
                .setSmallIcon(R.drawable.logo_notification)
                .setContentIntent(null)
                .setOngoing(true)
                .build();
        startForeground(9999,notification);
        GetForegroundAppMonitor task = new GetForegroundAppMonitor(context);
        task.execute();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel(true);
        try {
            if (mReceiver != null)
                unregisterReceiver(mReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (destroyService) {
            stopSelf();
            Utils.setIsServiceRunning(context, false);
        } else {
            newStartServiceMethod();
        }


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        task.cancel(true);
        if (mReceiver != null)
            unregisterReceiver(mReceiver);

        newStartServiceMethod();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void newStartServiceMethod() {

        Utils.setIsServiceRunning(context,true);

        Intent mIntent = new Intent(context, BackgroundAppMonitorService.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(mIntent);
/*
      # issue android 8.0
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            this.startForegroundService(mIntent);
        } else {
            this.startService(mIntent);
        }*/
    }
}
