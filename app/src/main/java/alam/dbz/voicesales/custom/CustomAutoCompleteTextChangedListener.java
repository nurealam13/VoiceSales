package alam.dbz.voicesales.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.activities.LoginActivity;
import alam.dbz.voicesales.activities.MainActivity;
import alam.dbz.voicesales.adapters.CompanyAutoCompleteAdapter;
import alam.dbz.voicesales.models.CompanyModel;

public class CustomAutoCompleteTextChangedListener implements TextWatcher{

    public static final String TAG = "CustomAuto";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        // if you want to see in the logcat what the user types
        Log.e(TAG, "User input: " + userInput);

        LoginActivity loginActivity = ((LoginActivity) context);

        // query the database based on the user input
        loginActivity.companyModelArrayList = loginActivity.getCompany();

        // update the adapater
        loginActivity.companyAutoCompleteAdapter.notifyDataSetChanged();
        loginActivity.companyAutoCompleteAdapter = new CompanyAutoCompleteAdapter(loginActivity,
                R.layout.item_autocomplete, loginActivity.companyModelArrayList);
        //loginActivity.companyAutoCompleteAdapter = new ArrayAdapter<CompanyModel>(loginActivity, android.R.layout.simple_dropdown_item_1line, loginActivity.companyModelArrayList);
        loginActivity.autoCompleteTextViewCompany.setAdapter(loginActivity.companyAutoCompleteAdapter);

    }

}