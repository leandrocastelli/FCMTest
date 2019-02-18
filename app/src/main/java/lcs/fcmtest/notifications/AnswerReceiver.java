package lcs.fcmtest.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lcs.fcmtest.database.DatabaseDAO;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

public class AnswerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"I am ALiveeeeee",Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(123);
        DatabaseDAO.getInstance().saveAnswer(context, bundle);
    }
}
