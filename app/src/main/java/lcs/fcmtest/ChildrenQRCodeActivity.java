package lcs.fcmtest;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lcs.fcmtest.services.BackgroundAppMonitorService;
import lcs.fcmtest.utils.AcessControl;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

import static lcs.fcmtest.services.BackgroundAppMonitorService.controlAcess;

public class ChildrenQRCodeActivity extends AppCompatActivity {

    ImageView QRCodeImg;
    String BD_QRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        BD_QRCode = bundle.getString(Constants.EMAIL_DATA_KEY);
        setContentView(R.layout.activity_children_qrcode);
        askForPermition();
        boolean isServiceRunning = Utils.getIsServiceRunning(this);

        controlAcess.changeFlagValue(true);
        startLockService(true,getApplicationContext());

        QRCodeImg = (ImageView) findViewById(R.id.QRCode);
        generateQRCode();


    }


    private void generateQRCode() {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(BD_QRCode, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.QRCode)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), getApplicationContext().getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
        return granted;
    }
    private void askForPermition() {
        if (!checkPermission()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    private void startLockService(boolean shouldStart, Context ctx) {
        final Intent it = new Intent(ChildrenQRCodeActivity.this, BackgroundAppMonitorService.class);
        BackgroundAppMonitorService.destroyService = !shouldStart;

        Utils.setIsServiceRunning(ChildrenQRCodeActivity.this, shouldStart);

        if (shouldStart) {
            ctx.startService(it);
            Toast.makeText(ChildrenQRCodeActivity.this, "Service is running.", Toast.LENGTH_LONG).show();
        } else {
            ctx.stopService(it);

        }

    }
}
