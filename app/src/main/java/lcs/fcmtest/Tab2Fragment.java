package lcs.fcmtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TableLayout;

/**
 * Created by User on 2/28/2017.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    TableLayout stk;

    View view;
    ExpandableListView elvCompra;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_fragment,container,false);

        //elvCompra = (ExpandableListView) view.findViewById(R.id.elvCompra);

        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        //ExpandableListAdapter adaptador = new ExpandableListAdapter(getContext());
        // define o apadtador do ExpandableListView
        //elvCompra.setAdapter(adaptador);


        return view;
    }





}
