package lcs.fcmtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;



//import com.google.api.services.samples.youtube.cmdline.Auth;

/**
 * Created by User on 2/28/2017.
 */

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    TableLayout stk;




    View view;
    ListView lv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_fragment,container,false);

        return view;
    }

    public Object getChild(int groupPosition) {
        // retorna um item do grupo
        return 1;
    }

    public Object getGroup(int groupPosition) {
        // retorna um grupo
        return 1;
    }

}
