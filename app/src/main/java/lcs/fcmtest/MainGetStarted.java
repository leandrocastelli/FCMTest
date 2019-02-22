package lcs.fcmtest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainGetStarted extends AppCompatActivity
{

    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    private Button btnStart;

    Tab1Fragment t1 = new Tab1Fragment();
    Tab2Fragment t2 = new Tab2Fragment();
    Tab3Fragment t3 = new Tab3Fragment();
    Tab4Fragment t4 = new Tab4Fragment();
    Tab5Fragment t5 = new Tab5Fragment();
    Tab6Fragment t6 = new Tab6Fragment();



    int idx=0;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager()); //tabs

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        btnStart = (Button) findViewById(R.id.btnGetStarted);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWizard = new Intent(MainGetStarted.this, MainActivityWizard.class);
                startActivity(intentWizard);
            }
        });
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);

        //TextView txtCampeonato = (TextView) findViewById(R.id.txtCampeonato);

    }


        public void onClick(View v) {

        idx++;
        if(idx>5)
            idx=0;



        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(t1, "Welcome");
        adapter.addFragment(t2, "tela 1");
        adapter.addFragment(t3, "tela 2");
        adapter.addFragment(t4, "tela 3");
        adapter.addFragment(t5, "tela 4");
        adapter.addFragment(t6, "Thank you");

            mViewPager.setAdapter(adapter);


    }


    private void setupViewPager(ViewPager viewPager) {


        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(t1, "Welcome");
        adapter.addFragment(t2, "tela 1");
        adapter.addFragment(t3, "tela 2");
        adapter.addFragment(t4, "tela 3");
        adapter.addFragment(t5, "tela 4");
        adapter.addFragment(t6, "Thank you");
        viewPager.setAdapter(adapter);

    }




}
