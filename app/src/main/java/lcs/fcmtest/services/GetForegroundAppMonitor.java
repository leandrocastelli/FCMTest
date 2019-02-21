package lcs.fcmtest.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import lcs.fcmtest.database.DatabaseDAO;
import lcs.fcmtest.utils.AcessControl;
import lcs.fcmtest.utils.PackagesAppUtil;
import lcs.fcmtest.utils.Utils;

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
        Log.d("Leandro", "doInBackground GetForegroundAppMonitor");
        String lastPackage = "lcs.fcmtest";
        while (keepRunning && !isCancelled()) {


            String packageName = PackagesAppUtil.getForegroundApp(context);
            Log.d("Leandro", "package " + packageName);
            //Log.d("AsyncTask Fore", packageName.toString() );
            // Log.d("AsyncTask Back", lastPackageName.toString() );
            // Log.d("AsyncTaskFlagsFlagValue", String.valueOf(flagIsAuthenticated));
            if (lastPackage.equals(packageName))
                continue;
            else {
                lastPackage = packageName;
            }
            boolean control = AcessControl.isLoggedIn();

            if ("".equals(packageName)) {
                continue;
            }
            if (!"lcs.fcmtest".equals(packageName) && AcessControl.isLoggedIn())
                DatabaseDAO.getInstance().checkIfBlocked(context, Utils.getUserPreference(context),packageName);

        }
        return null;
    }

    @Override
    protected void onCancelled() {
        Log.d("Leandro","GetForegroundAppMonitor canceled");
        super.onCancelled();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
