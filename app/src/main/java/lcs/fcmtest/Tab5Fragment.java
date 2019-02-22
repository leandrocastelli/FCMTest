package lcs.fcmtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TableLayout;

/**
 * Created by User on 2/28/2017.
 */

public class Tab5Fragment extends Fragment {
    private static final String TAG = "Tab5Fragment";

    TableLayout stk;

    View view;
    WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.tab5_fragment, container, false);


        return view;
    }



}
