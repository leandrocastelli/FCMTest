package lcs.fcmtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000; //splash screen will be shown for 2 seconds

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_splash);
        String role = Utils.getRolePreference(this);
        final Intent mainIntent;
        switch (role) {
            case "parent": {
                mainIntent = new Intent(this, ParentMainActivity.class);
            } break;
            case "children": {
                mainIntent = new Intent(this, ChildrenQRCodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EMAIL_DATA_KEY, Utils.getEmailPreference(this).split("@")[0]);
                bundle.putString(Constants.NAME_DATA_KEY, Utils.getNamePreference(this));
                mainIntent.putExtras(bundle);
            }break;
            default: {
                mainIntent = new Intent(this, MainGetStarted.class);
            }

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
