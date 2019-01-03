package alam.dbz.voicesales.data;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Data {
    public static String DB_PATH;
    public static String DB_NAME;
    public static final String TAG = "com.alam.voiceSales";

    public Data(Context context) {
        DB_NAME = "SalesOrderTracking.sqlite";
        DB_PATH = context.getExternalFilesDir(null).getAbsolutePath() + "/";

    }

    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        return df.format(Calendar.getInstance().getTime());
    }

}
