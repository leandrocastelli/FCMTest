package lcs.fcmtest.services;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

import lcs.fcmtest.ChildrenQRCodeActivity;
import lcs.fcmtest.database.DatabaseDAO;
import lcs.fcmtest.notifications.NotificationHandler;
import lcs.fcmtest.utils.AcessControl;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

import static lcs.fcmtest.services.BackgroundAppMonitorService.controlAcess;


public class FirebaseService extends FirebaseMessagingService {


    private static final String TAG = "FirebaseService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, " Message Received from " + remoteMessage.getFrom());
        NotificationHandler notificationHandler = new NotificationHandler(getApplicationContext());
        Notification notification = null;
        Map<String,String> data;
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Handle the notification here " + remoteMessage.getNotification().getBody());
            RemoteMessage.Notification remoteNotification = remoteMessage.getNotification();
            notification = notificationHandler.createNotification(remoteNotification);

        } else {
            //Need to handle SETUP finish
            data = remoteMessage.getData();
            if (data.containsKey(Constants.SETUP_CONCLUDED)) {
                String parentUserName = data.get(Constants.SENDER_ID);
                DatabaseDAO.getInstance().getParentId(getApplicationContext(),parentUserName);
            } else {
                notification = notificationHandler.createNotification(data.get(Constants.APP_NAME), data.get(Constants.PACKAGE_NAME), data.get(Constants.SENDER_ID), data.get(Constants.SENDER_NAME));
            }
        }
        if (notification != null)
            notificationHandler.postNotification(123,notification);





    }
    private void startLockService(boolean shouldStart, Context ctx) {
        final Intent it = new Intent(ctx, BackgroundAppMonitorService.class);
        BackgroundAppMonitorService.destroyService = !shouldStart;

        Utils.setIsServiceRunning(ctx, shouldStart);

        if (shouldStart) {
            ctx.startForegroundService(it);
            controlAcess.changeFlagValue(true);
        } else {
            ctx.stopService(it);

        }

    }

}
