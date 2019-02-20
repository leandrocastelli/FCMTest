package lcs.fcmtest.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import lcs.fcmtest.model.InfoAboutInstalledApps;
import lcs.fcmtest.utils.PackagesAppUtil;


public class GetListOfInstalledApps extends AsyncTask<Void, Void, List<InfoAboutInstalledApps>> {

    private List<InfoAboutInstalledApps> mListOfInstalledApps;
    private Context context;
    public AsyncResponse delegate = null;




    public GetListOfInstalledApps(Context context, AsyncResponse delegate) {
        this.context = context;
        this.delegate = delegate;
        Log.d("RemoteCare", "constructor GetListOfInstalledApps");
    }


    @Override
    protected void onPreExecute() {
        Log.d("RemoteCare", "onPreExecute GetListOfInstalledApps");

    }

    @Override
    protected void onPostExecute(List<InfoAboutInstalledApps> InfoAboutInstalledApps) {
        Log.d("RemoteCare", "onPostExecute GetListOfInstalledApps");
       delegate.processFinish(InfoAboutInstalledApps);

    }

    @Override
    protected void onCancelled(List<InfoAboutInstalledApps> InfoAboutInstalledApps) {
        Log.d("RemoteCare", "onCancelled GetListOfInstalledApps");
        super.onCancelled(InfoAboutInstalledApps);
    }

    @Override
    protected List<InfoAboutInstalledApps> doInBackground(Void... voids) {
        Log.d("RemoteCare", "doInBackground GetListOfInstalledApps");
        PackagesAppUtil utilPackage = new PackagesAppUtil(context);
        mListOfInstalledApps = utilPackage.getAllInfoPackages();

        Log.d("RemoteCare", mListOfInstalledApps.toString());

        return mListOfInstalledApps;
    }

    public interface AsyncResponse {
        void processFinish(List<InfoAboutInstalledApps> output);
    }

}
