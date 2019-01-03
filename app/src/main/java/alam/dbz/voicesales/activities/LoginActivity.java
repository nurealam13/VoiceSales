package alam.dbz.voicesales.activities;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.adapters.CompanyAutoCompleteAdapter;
import alam.dbz.voicesales.adapters.CustomerAutoCompleteAdapter;
import alam.dbz.voicesales.adapters.LocationAutoCompleteAdapter;
import alam.dbz.voicesales.api.ApiInterface;
import alam.dbz.voicesales.api.LoginListModel;
import alam.dbz.voicesales.api.RetrofitInstance;
import alam.dbz.voicesales.appmanager.AppManager;
import alam.dbz.voicesales.custom.CustomAutoCompleteTextChangedListener;
import alam.dbz.voicesales.data.AppPreferences;
import alam.dbz.voicesales.data.Data;
import alam.dbz.voicesales.databasemanager.DataBaseManager;
import alam.dbz.voicesales.models.CompanyListModel;
import alam.dbz.voicesales.models.CompanyModel;
import alam.dbz.voicesales.models.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    public static AutoCompleteTextView autoCompleteTextViewCompany, autoCompleteTextViewLocation;
    private EditText editTextUserName, editTextPassword;
    private ArrayList<LoginModel> loginModels = new ArrayList<>();
    public static ArrayList<CompanyModel> companyModelArrayList = new ArrayList<>();
    public static ArrayList<CompanyModel> locationArrayList = new ArrayList<>();
    public static CompanyAutoCompleteAdapter companyAutoCompleteAdapter;
    private LocationAutoCompleteAdapter locationAutoCompleteAdapter;
    private String companyId = "0", locationId = "0";
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiInitialization();
        if (companyModelArrayList.size() < 1) {
            fetchCompany();
        }
        setCompany();
    }

    public ArrayList<CompanyModel> getCompany() {
        return companyModelArrayList;
    }

    private void uiInitialization() {
        autoCompleteTextViewCompany = findViewById(R.id.autoCompleteTextViewCompany);
        autoCompleteTextViewLocation = findViewById(R.id.autoCompleteTextViewLocation);

        editTextUserName = findViewById(R.id.edtUserName);
        editTextPassword = findViewById(R.id.edtPassword);

        autoCompleteTextViewCompany.setOnTouchListener(this);
        autoCompleteTextViewLocation.setOnTouchListener(this);

        autoCompleteTextViewLocation.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
        companyModelArrayList = new DataBaseManager(this).getCompanyList();


        autoCompleteTextViewCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);

                // autoCompleteTextViewCompany.;

                if (companyModelArrayList.size() > 0) {
                    for (int i = 0; i < companyModelArrayList.size(); i++) {
                        String cName = companyModelArrayList.get(i).getCompanyName();
                        Log.i(Data.TAG, "--1--: " + cName);
                        Log.i(Data.TAG, "--2--: " + selected);
                        if (cName.equals(selected)) {
                            companyId = companyModelArrayList.get(i).getCompanyId();
                            locationArrayList = new DataBaseManager(LoginActivity.this).getLocation(companyId);
                            Log.i(Data.TAG, companyId + " :" + companyModelArrayList.get(i).getCompanyName());
                            i = companyModelArrayList.size();
                        }
                    }
                    setLocation();
                }

                // Toast.makeText(LoginActivity.this, companyId, Toast.LENGTH_LONG).show();
            }
        });


        autoCompleteTextViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);

                if (locationArrayList.size() > 0) {
                    for (int i = 0; i < locationArrayList.size(); i++) {
                        String lName = locationArrayList.get(i).getLocationName();
                        if (lName.equals(selected)) {
                            locationId = locationArrayList.get(i).getLocationId();
                            Log.i(Data.TAG, locationId + " :" + locationArrayList.get(i).getCompanyName());
                            i = locationArrayList.size();
                        }
                    }
                }


            }
        });
    }

    void setCompany() {
        companyAutoCompleteAdapter = new CompanyAutoCompleteAdapter(this,
                R.layout.item_autocomplete, companyModelArrayList);
        autoCompleteTextViewCompany.setAdapter(companyAutoCompleteAdapter);
    }

    void setLocation() {

        if (locationArrayList.size() > 0) {
            locationAutoCompleteAdapter = new LocationAutoCompleteAdapter(this,
                    R.layout.item_autocomplete, locationArrayList);
            autoCompleteTextViewLocation.setAdapter(locationAutoCompleteAdapter);
        }
    }

    public void signIn(View v) {
        loginStatus();

    }

    private void fetchCompany() {
        // display a progress dialog

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<CompanyListModel> call = service.getCompany("getCompanyLocation");
        Log.wtf(Data.TAG, call.request().url() + "");
        call.enqueue(new Callback<CompanyListModel>() {
            @Override
            public void onResponse(Call<CompanyListModel> call, Response<CompanyListModel> response) {
                if (response.body() != null) {
                    ArrayList<CompanyModel> arrayList = new ArrayList<>();
                    arrayList = response.body().getCompanyModelArrayList();

                    if (arrayList.size() > 0) {
                        companyModelArrayList.addAll(arrayList);
                        addLocalCompany();

                        Log.i(Data.TAG, "Size: " + companyModelArrayList.size());
                    }

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CompanyListModel> call, Throwable t) {
                Log.e(Data.TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addLocalCompany() {

        for (int i = 0; i < companyModelArrayList.size(); i++) {
            String companyName = companyModelArrayList.get(i).getCompanyName() + " (" + companyModelArrayList.get(i).getCode() + ")";

            new DataBaseManager(this).addCompany(companyModelArrayList.get(i).getCompanyId(), companyModelArrayList.get(i).getCode(),
                    companyName, "", "");

            boolean isExistLocation = new DataBaseManager(this)
                    .CheckLocation(companyModelArrayList.get(i).getCompanyId(), companyModelArrayList.get(i).getLocationId());

            if (!isExistLocation) {

                new DataBaseManager(this).addLocation(companyModelArrayList.get(i).getLocationId(),
                        companyModelArrayList.get(i).getCompanyId(), "", companyModelArrayList.get(i).getLocationName(), "", "");
            }
        }
        progressDialog.dismiss();
        companyModelArrayList.clear();
        companyModelArrayList = new DataBaseManager(this).getCompanyList();
        setCompany();


    }

    private void loginStatus() {
        // display a progress dialog

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginListModel> call = service.getLoginStatus("checkLogin", companyId, locationId, editTextUserName.getText().toString(), editTextPassword.getText().toString(), "3", "");
        Log.wtf(Data.TAG, call.request().url() + "");
        call.enqueue(new Callback<LoginListModel>() {
            @Override
            public void onResponse(Call<LoginListModel> call, Response<LoginListModel> response) {
                Log.e(Data.TAG, response.message() + "---");
                Log.e(Data.TAG, response.errorBody() + ": ---");
//                Log.e(Data.TAG, response.body().toString());
                if (response.body().getLoginModels() != null) {
                    loginModels = response.body().getLoginModels();
                    if (loginModels.size() > 0) {
                        if (loginModels.get(0).getSuccess().equals("1")) {
                            new AppPreferences(getApplicationContext()).setLoginDate(Data.getDate());
                            new AppPreferences(getApplicationContext()).setCompanyId(companyId);
                            new AppPreferences(getApplicationContext()).setAreaName(loginModels.get(0).getAreaName());
                           // new AppPreferences(getApplicationContext()).setAreaId(loginModels.get(0).getAreaId());
                            new AppPreferences(getApplicationContext()).setUserId(loginModels.get(0).getUserId());
                            new AppPreferences(getApplicationContext())
                                    .setDistributorId(loginModels.get(0).getDistributorId());
                            new AppPreferences(getApplicationContext())
                                    .setDistributorName(loginModels.get(0).getDistributorName());

                            new AppManager(LoginActivity.this)
                                    .SetIntent(MainActivity.class);

                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginListModel> call, Throwable t) {
                Log.e(Data.TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int DRAWABLE_RIGHT = 2;


        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

            if (view.getId() == R.id.autoCompleteTextViewCompany) {
                if (motionEvent.getRawX() >= (autoCompleteTextViewCompany.getRight() - autoCompleteTextViewCompany.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    Toast.makeText(getApplicationContext(), "Company", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else if (view.getId() == R.id.autoCompleteTextViewLocation) {
                if (motionEvent.getRawX() >= (autoCompleteTextViewLocation.getRight() - autoCompleteTextViewLocation.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    Toast.makeText(getApplicationContext(), "Location", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else if (view.getId() == R.id.edtUserName) {
                if (motionEvent.getRawX() >= (editTextUserName.getRight() - editTextUserName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    Toast.makeText(getApplicationContext(), "User Name", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else if (view.getId() == R.id.edtPassword) {
                if (motionEvent.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    Toast.makeText(getApplicationContext(), "Password", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }
}
