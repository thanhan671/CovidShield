package com.thanhan.covidshield;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQ_CODE = 100;
    private static final int REQUEST_CODE_SCAN_ONE = 222;
    final int PERMISSIONS_LENGTH = 2;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // CAMERA_REQ_CODE is user-defined and is used to receive the request code of the permission verification result.
        this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQ_CODE);
        // QRCODE_SCAN_TYPE and DATAMATRIX_SCAN_TYPE are set for the barcode format, indicating that Scan Kit will support only QR code and Data Matrix.
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE, HmsScan.ALL_SCAN_TYPE).create();
        ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, options);
    }
    // Use the onRequestPermissionsResult function to receive the permission verification result.

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Check whether requestCode is set to the value of CAMERA_REQ_CODE during permission application, and then check whether the permission is enabled.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQ_CODE && grantResults.length == PERMISSIONS_LENGTH && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // Call the barcode scanning API to build the scanning capability.

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            // Input an image for scanning and return the result.
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                // Display the parsing result.
                Toast.makeText(MainActivity.this, "result after scan"+obj.originalValue+"\n"+obj.describeContents(), Toast.LENGTH_LONG).show();
            }
        }
    }
}