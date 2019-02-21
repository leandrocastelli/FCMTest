package lcs.fcmtest;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lcs.fcmtest.services.BackgroundAppMonitorService;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;

import static lcs.fcmtest.services.BackgroundAppMonitorService.controlAcess;

public class ChildrenQRCodePostActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_after_qr_code);

    }


}
