package lcs.fcmtest.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;

import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.R;
public class NotificationHandler {
    public NotificationHandler(Context context) {
        this.context = context;
    }
    private Context context;
    private NotificationChannel createNotificationChannel() {
        final NotificationChannel channel = new NotificationChannel("Default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("No description");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        return channel;
    }

    public Notification createNotification(String appName, String packageName, String sender_id, String senderName) {
        Intent intentOK = new Intent(context, AnswerReceiver.class);
        Intent intentCancel = new Intent(context, AnswerReceiver.class);

        intentOK.putExtra("answer","allow");
        intentOK.putExtra("appName",appName);
        intentOK.putExtra("packageName",packageName);
        intentOK.putExtra("sender_id",sender_id);

        intentCancel.putExtra("answer","block");
        intentCancel.putExtra("appName",appName);
        intentCancel.putExtra("packageName",packageName);
        intentCancel.putExtra("sender_id",sender_id);

        PendingIntent pendingIntentOK = PendingIntent.getBroadcast(context,2,intentOK,PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context,0,intentCancel,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, createNotificationChannel().getId())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)

                .setContentTitle("Seu filho " + senderName + " abriu uma nova aplicação")
                .setContentText("Permitir " + appName + " ?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(new NotificationCompat.Action.Builder(R.drawable.icon_like, "Permitir", pendingIntentOK).build())
                .addAction(new NotificationCompat.Action.Builder(R.drawable.icon_dislike,"Bloquear", pendingIntentCancel).build());

        return mBuilder.build();
    }
    public Notification createNotification (RemoteMessage.Notification remoteNotification) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, createNotificationChannel().getId())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(remoteNotification.getTitle())
                .setContentText(remoteNotification.getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return mBuilder.build();
    }
    public void postNotification(int id, Notification notification) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id,notification);
    }
}
