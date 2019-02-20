package lcs.fcmtest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lcs.fcmtest.utils.Constants;

public class ChildrenQRCodeActivity extends AppCompatActivity {

    ImageView QRCodeImg;
    String BD_QRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        BD_QRCode = bundle.getString(Constants.EMAIL_DATA_KEY);
        setContentView(R.layout.activity_children_qrcode);

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
}
