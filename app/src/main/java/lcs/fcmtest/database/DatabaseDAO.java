package lcs.fcmtest.database;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lcs.fcmtest.model.Children;
import lcs.fcmtest.model.Person;
import lcs.fcmtest.utils.Constants;

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
                    .child(parentId).setValue(true);
        }

    }

}
