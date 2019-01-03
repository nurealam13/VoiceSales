package alam.dbz.voicesales.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.alert.NetworkAlert;
import alam.dbz.voicesales.appmanager.AppManager;
import alam.dbz.voicesales.data.AppPreferences;
import alam.dbz.voicesales.data.Data;
import alam.dbz.voicesales.network.NetworkUtil;

public class SplashActivity extends Activity {


    private AppPreferences appPreferences;
    private ProgressDialog progressDialog;
    //    private int log = 0;
//    private int net = 0;
    private NetworkAlert alert;
    private String curr_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Data(this);
        appPreferences = new AppPreferences(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        curr_Date = df.format(Calendar.getInstance().getTime());


        alert = new NetworkAlert(this);
        final int status = NetworkUtil.getConnectivityStatus(this);

        File file = new File(Data.DB_PATH, Data.DB_NAME);

        if (file.exists()) {


            double bytes = file.length();
            double kilobytes = (bytes / 1024);
            System.out.println("File Exist kilobytes---" + kilobytes);
            Log.i(Data.TAG, "File Exist kilobytes---" + kilobytes);

            if (kilobytes < 16) {
                if (file.delete()) {
                    Log.i(Data.TAG, "File Exist And Deleted--" + kilobytes);
                    copyAssets();
                }
            }

        } else {
            copyAssets();

        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (status != 0) {
                    if (new AppPreferences(SplashActivity.this).getLoginDate().equals(curr_Date)) {
                        new AppManager(SplashActivity.this)
                                .SetIntent(MainActivity.class);
                      //  finish();
                    } else {
                        new AppManager(SplashActivity.this)
                                .SetIntent(LoginActivity.class);
                        finish();
                    }
                } else {

                    alert.setAlertDialog("Network Problem",
                            " Network Connection  Not Available");

                }

                progressDialog.dismiss();
            }
        }, 3 * 1000);

    }

    private void copyAssets() {

        AssetManager assetManager = getAssets();
        String[] files = null;
        InputStream in = null;
        OutputStream out = null;
        // String filename = "filename.ext";
        try {
            in = assetManager.open(Data.DB_NAME);
            out = new FileOutputStream(Data.DB_PATH + Data.DB_NAME);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (IOException e) {
            Log.e(Data.TAG, "SplashActivity: Failed to copy asset file: " + Data.DB_NAME, e);
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
