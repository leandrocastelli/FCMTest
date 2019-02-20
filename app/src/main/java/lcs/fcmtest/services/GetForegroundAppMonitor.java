package lcs.fcmtest.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import lcs.fcmtest.utils.PackagesAppUtil;

import static lcs.fcmtest.services.BackgroundAppMonitorService.controlAcess;


/**
 * Created by lrosa on 31/01/2018.
 */

public class GetForegroundAppMonitor extends AsyncTask<Void, Void, Void> {

    private Context context;
    boolean keepRunning = true;


    public GetForegroundAppMonitor(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Log.d("SidiAppLocker", "doInBackground GetForegroundAppMonitor");

        while (keepRunning && !isCancelled()) {

            String packageName = PackagesAppUtil.getForegroundApp(context);

            //Log.d("AsyncTask Fore", packageName.toString() );
            // Log.d("AsyncTask Back", lastPackageName.toString() );
            // Log.d("AsyncTaskFlagsFlagValue", String.valueOf(flagIsAuthenticated));

            if (checkIfPackageIsBlocked(packageName) && !controlAcess.isLoggedIn()) {
                Intent it = new Intent(context, LockActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                it.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(it);

            } else if (!(checkIfPackageIsBlocked(packageName)) && !(packageName.equals("")) && !(packageName.equals("com.protocol.newpack.lockapp")) && controlAcess.isLoggedIn()) {
                controlAcess.changeFlagValue(false);
            }
        }
        return null;
    }

    private boolean checkIfPackageIsBlocked(String packageName){
        if (MainActivity.listofBlockedPackages != null)
            for (int i = 0; i < MainActivity.listofBlockedPackages.size(); i++) {
                if(packageName.equals(MainActivity.listofBlockedPackages.get(i)))
                    return true;
            }
            return false;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("SidiAppLocker", "onCancelled GetForegroundAppMonitor");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
