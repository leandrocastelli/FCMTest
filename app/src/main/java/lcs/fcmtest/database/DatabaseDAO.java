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

import java.util.List;

import lcs.fcmtest.LockActivity;
import lcs.fcmtest.model.Children;
import lcs.fcmtest.model.InfoAboutInstalledApps;
import lcs.fcmtest.model.Person;
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

    public void saveAnswer(Context context, final Bundle bundle) {
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
                        + "status");
                ref.setValue(bundle.getString("answer"));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void linkParentChild(Context context, String parent, String child) {
        FirebaseApp.initializeApp(context);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Constants.PARENTS_DATABASE +
                "/" + parent + "/childrens/" + child).setValue(true);
        database.getReference(Constants.CHILDRENS_DATABASE +
                "/" + child + "/parents/" + parent).setValue(true);
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
                InfoAboutInstalledApps value = dataSnapshot.getValue(InfoAboutInstalledApps.class);
                if ("blocked".equals(value.getStatus())) {
                    //App is blocked, let's check the ask again flag
                    //TODO create flag
                    Intent it = new Intent(context, LockActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    it.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    it.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(it);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
