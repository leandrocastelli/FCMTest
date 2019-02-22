package lcs.fcmtest.database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lcs.fcmtest.LockActivity;
import lcs.fcmtest.model.Children;
import lcs.fcmtest.model.InfoAboutInstalledApps;
import lcs.fcmtest.model.Person;
import lcs.fcmtest.notifications.MessageHandler;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

public class DatabaseDAO {
    private static DatabaseDAO instance;

    private DatabaseDAO(){};

    public static DatabaseDAO getInstance(){
        if (instance == null) {
            instance = new DatabaseDAO();
        }
        return instance;
    }

    public void savePerson (Context context, Person person) {
        String table;
        if (person instanceof Children) {
            table = Constants.CHILDRENS_DATABASE;
        } else {
            table = Constants.PARENTS_DATABASE;
        }
        String username = person.getEmail().split("@")[0];

        FirebaseApp.initializeApp(context);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(table).child(username).setValue(person);
        if (person instanceof Children) {
            String parentId = ((Children)person).getParentEmail().split("@")[0];
            database.child(table).child(username).child(Constants.PARENTS_DATABASE)
                    .child(parentId).setValue(false);
        }

    }

    public void saveAnswer(final Context context, final Bundle bundle) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference appRef = database.getReference(Constants.CHILDRENS_DATABASE +
                "/" + bundle.get("sender_id") + "/id");
        appRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fcmId = dataSnapshot.getValue(String.class);
                final DatabaseReference ref = database.getReference(Constants.CHILDRENS_DATABASE +
                        "/" + bundle.get("sender_id") + "/appList/"
                        + Utils.convertPackageNameToPath(bundle.getString("packageName"))
                        + "/status");
                ref.setValue(bundle.getString("answer"));
                new MessageHandler().answerBlockApp(context, bundle.getString("appName"),bundle.getString("answer"),fcmId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void linkParentChild(final Context context, final String parent, final String child) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Constants.PARENTS_DATABASE +
                "/" + parent + "/childrens/" + child).setValue(true);
        final DatabaseReference childRef =database.getReference(Constants.CHILDRENS_DATABASE +
                        "/" + child );
        childRef.child("parents").child(parent).setValue(true);
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.child("id").getValue(String.class);
                MessageHandler messageHandler = new MessageHandler();
                String jsonString = String.format(Constants.JSON_SETUP_CONCLUDED, parent,"true",id);
                try {
                    messageHandler.sendMessage(context, new JSONObject(jsonString));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                childRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAppList(Context context, String id, List<InfoAboutInstalledApps> list) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (InfoAboutInstalledApps app : list) {
            DatabaseReference reference = database.getReference(Constants.CHILDRENS_DATABASE +
                    "/" + id + "/appList/" + Utils.convertPackageNameToPath(app.getPname()));
            reference.setValue(app);
        }

    }

    public void checkIfBlocked(final Context context, String id, String packageName) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(Constants.CHILDRENS_DATABASE +
                "/" + id + "/appList/" + Utils.convertPackageNameToPath(packageName));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild("status"))
                    return;
                InfoAboutInstalledApps value = dataSnapshot.getValue(InfoAboutInstalledApps.class);
                if ("blocked".equals(value.getStatus())) {
                    //App is blocked, let's check the ask again flag
                    //TODO create flag
                    Intent it = new Intent(context.getApplicationContext(), LockActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    it.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    it.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(it);
                    new MessageHandler().askPermissionFatherApp(context, value.getPname(), value.getAppname());

                }
                reference.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getParentId(final Context context, final String parentUser) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(Constants.PARENTS_DATABASE).child(parentUser).child("id");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String parentId = dataSnapshot.getValue(String.class);
                Utils.setParentPreference(context, parentId);
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
