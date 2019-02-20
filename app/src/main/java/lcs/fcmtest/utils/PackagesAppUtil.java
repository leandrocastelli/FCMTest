package lcs.fcmtest.utils;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lcs.fcmtest.model.InfoAboutInstalledApps;


public class PackagesAppUtil {

    private Context context;

    public PackagesAppUtil(Context context) {
        Log.d("RemoteCare", "Constructor PackagesAppUtil");
        this.context = context;
    }


    public static String getForegroundApp(Context context) {

        //Log.d("RemoteCare", "getForegroundApp PackagesAppUtil");

        String topPackageName = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            long time = System.currentTimeMillis();

            UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 30, System.currentTimeMillis() + (10 * 1000));
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
            }

            if (event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                return event.getPackageName();
            } else {
                topPackageName = "";
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;

            topPackageName = componentInfo.getPackageName();
        }
        return topPackageName;
    }


    public ArrayList<InfoAboutInstalledApps> getAllInfoPackages() {
        //Log.d("RemoteCare", "getAllInfoPackages PackagesAppUtil");
        ArrayList<InfoAboutInstalledApps> apps = getInstalledApps();
        return apps;
    }

    private ArrayList<InfoAboutInstalledApps> getInstalledApps() {
       // Log.d("RemoteCare", "getInstalledApps PackagesAppUtil");
        ArrayList<InfoAboutInstalledApps> res = new ArrayList<InfoAboutInstalledApps>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);

            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                InfoAboutInstalledApps newInfo = new InfoAboutInstalledApps();
                newInfo.setAppname(p.applicationInfo.loadLabel(context.getPackageManager()).toString());
                newInfo.setPname(p.packageName);
                newInfo.setVersionName(p.versionName);
                newInfo.setVersionCode(p.versionCode);
                res.add(newInfo);
            }
        }
        return res;
    }
}
