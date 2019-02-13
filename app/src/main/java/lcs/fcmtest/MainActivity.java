package lcs.fcmtest;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import lcs.fcmtest.database.DatabaseDAO;
import lcs.fcmtest.model.Children;
import lcs.fcmtest.model.Parent;
import lcs.fcmtest.model.Person;
import lcs.fcmtest.notifications.MessageHandler;
import lcs.fcmtest.utils.Utils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO REMOVE THIS - ONLY FOR DEVELOPMENT
        //Utils.setRolePreference(this,"");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Leandro", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Utils.setTokenPreference(getApplication(), token);
                    }
                });
        Button button = findViewById(R.id.buttonSendMsg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageHandler handler = new MessageHandler();
                handler.sendMessage(v.getContext(), "f28YC-t6zqE:APA91bHSQlA2tjYOyxVZS7QMKYXGRTHpLF9vVPe_xLAsWSov1lCUxu8gh0ZiQBSPssYeXpKCDPdcASL_zv_FJbO55y4zx9AJEUkBilTHYOSdg8Przw5HQGVI1udDOL730ghEtnAd0v6c");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String role = Utils.getRolePreference(this);
        if (role.isEmpty()) {
            createRoleDialog();
        }
    }


    private void createRoleDialog() {
        final  EditText edtName;
        final  EditText ownEmail;
        final  EditText parentEmail;
        final RadioGroup rgRole;
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);

        edtName = (EditText) view.findViewById(R.id.edtName);
        ownEmail = (EditText)view.findViewById(R.id.edtOwnEmail);
        parentEmail = (EditText)view.findViewById(R.id.edtParentEmail);
        rgRole = (RadioGroup) view.findViewById(R.id.rdGroupRole);
        rgRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbParent:
                        parentEmail.getText().clear();
                        parentEmail.setEnabled(false);


                        break;
                    case R.id.rbSon:
                        parentEmail.setEnabled(true);

                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edtName.getText().toString();
                        String email = ownEmail.getText().toString();
                        String username = email.split("@")[0];
                        String token = Utils.getTokenPreference(getApplicationContext());
                        String role = "";
                        Person person = null;
                        switch (rgRole.getCheckedRadioButtonId()) {
                            case R.id.rbParent:
                                role = "parent";
                                //Do the Parent thing here.
                                person = new Parent(name,email,token,null);
                            break;
                            case R.id.rbSon:
                                role = "children";
                                //Do children stuff here
                                String pEmail = parentEmail.getText().toString();
                                person = new Children(name, email, token, pEmail);
                            break;
                        }
                        DatabaseDAO.getInstance().savePerson(getApplicationContext(), person);
                        Utils.setRolePreference(view.getContext(), role);
                    }
                });
        builder.create().show();

    }

}
