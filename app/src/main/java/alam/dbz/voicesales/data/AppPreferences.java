package alam.dbz.voicesales.data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "SaleOrder_preferences";
    private SharedPreferences apppSharedPreferences;
    private SharedPreferences.Editor preferencesEditor;

    public AppPreferences(Context context) {
        apppSharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS,
                Context.MODE_PRIVATE);
        preferencesEditor = apppSharedPreferences.edit();

    }



    public String getLoginDate() {
        return apppSharedPreferences.getString("LoginDate", "");
    }

    public void setLoginDate(String value) {
        preferencesEditor.putString("LoginDate", value);
        preferencesEditor.commit();
    }


    public void setCompanyId(String value) {
        preferencesEditor.putString("CompanyId", value);
        preferencesEditor.commit();
    }

    public String getCompanyId() {
        return apppSharedPreferences.getString("CompanyId", "");
    }

    public String getAreaId() {
        return apppSharedPreferences.getString("AreaId", "2");
    }

    public void setAreaId(String value) {
        preferencesEditor.putString("AreaId", value);
        preferencesEditor.commit();
    }

    public String getAreaName() {
        return apppSharedPreferences.getString("AreaName", null);
    }

    public void setAreaName(String value) {
        preferencesEditor.putString("AreaName", value);
        preferencesEditor.commit();
    }


    public String getUserId() {
        return apppSharedPreferences.getString("userId", null);
    }

    public void setUserId(String value) {
        preferencesEditor.putString("userId", value);
        preferencesEditor.commit();
    }

    public String getDistributorName() {
        return apppSharedPreferences.getString("distributorName", null);
    }

    public void setDistributorName(String value) {
        preferencesEditor.putString("distributorName", value);
        preferencesEditor.commit();
    }

    public String getDistributorId() {
        return apppSharedPreferences.getString("distributorId", null);
    }

    public void setDistributorId(String value) {
        preferencesEditor.putString("distributorId", value);
        preferencesEditor.commit();
    }
}
