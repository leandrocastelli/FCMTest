package lcs.fcmtest.services;
import android.app.Notification;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import lcs.fcmtest.notifications.NotificationHandler;
import lcs.fcmtest.utils.Constants;


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
            data = remoteMessage.getData();
            notification = notificationHandler.createNotification(data.get(Constants.APP_NAME),data.get(Constants.PACKAGE_NAME), data.get(Constants.SENDER_ID), data.get(Constants.SENDER_NAME));
        }
        notificationHandler.postNotification(123,notification);





    }

}
