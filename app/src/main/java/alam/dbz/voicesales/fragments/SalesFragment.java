package alam.dbz.voicesales.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.adapters.CustomerAutoCompleteAdapter;
import alam.dbz.voicesales.adapters.CustomerListVoiceAdapter;
import alam.dbz.voicesales.adapters.ProductAutoCompleteAdapter;
import alam.dbz.voicesales.adapters.ProductListVoiceAdapter;
import alam.dbz.voicesales.adapters.SpinnerUnitTypeAdapter;
import alam.dbz.voicesales.api.ApiInterface;
import alam.dbz.voicesales.api.RetrofitInstance;
import alam.dbz.voicesales.data.AppPreferences;
import alam.dbz.voicesales.data.Data;
import alam.dbz.voicesales.data.RoundConvert;
import alam.dbz.voicesales.databasemanager.DataBaseManager;
import alam.dbz.voicesales.models.CompanyModel;
import alam.dbz.voicesales.models.CustomerListModel;
import alam.dbz.voicesales.models.CustomerModel;
import alam.dbz.voicesales.models.ProductListModel;
import alam.dbz.voicesales.models.ProductModel;
import alam.dbz.voicesales.models.SaleOrder;
import alam.dbz.voicesales.models.UnitType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    ArrayList<SaleOrder> saleOrdersList = new ArrayList<SaleOrder>();

    private int REQ_CODE_SPEECH_INPUT = 101;
    private ArrayList<ProductModel> productListModels = new ArrayList<>();
    private ArrayList<CustomerModel> customerModels = new ArrayList<>();
    private ArrayList<CompanyModel> companyModelArrayList = new ArrayList<>();

    //private ArrayList<ProductModel> productListLocal = new ArrayList<>();
    private ArrayList<ProductModel> productListLocalTemp = new ArrayList<>();
    // private ArrayList<CustomerModel> customersListLocal = new ArrayList<>();
    private ArrayList<CustomerModel> customersListLocalTemp = new ArrayList<>();
    private ProductAutoCompleteAdapter adapter;
    private CustomerAutoCompleteAdapter customerAutoCompleteAdapter;
    int j = 0;
    private int ids = 1000;
    ArrayList<UnitType> UnitTypeList = new ArrayList<UnitType>();

    private LinearLayout saleOrderStatusLinearLayout;
    EditText editTextQuantity, editTextDiscount, editTextPrice, editTextCash, editTextCredit;
    AutoCompleteTextView autoCompleteTextViewCustomers, autoCompleteTextViewProducts;
    Spinner spinnerPaymentType, spinnerUnitType;
    private String buyerName = null;
    private String productId, productName, brandName, sku, priceUnit, productDiscount, productUniTypeId, productUniType;
    private int salesQuantity = 0;
    private String paymentType = "";
    private Button voiceButton, submitButton, clearButton, newButton;
    String buyerId;
    private Dialog dialog;
    private int selectedRow;

    private LinearLayout payment_layout;
    private double discount = 0.0;
    private double totalPayableAmount = 0.0;

    public SalesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sales, container, false);

        uiInitialization(v);

        productListModels = new DataBaseManager(getActivity()).viewProductsVoice();
        if (productListModels.size() > 0) {
            setProduct();
        } else {
            fetchProduct();
        }

        customerModels = new DataBaseManager(getActivity())
                .viewCustomers(new AppPreferences(getActivity()).getAreaId());


        if (customerModels.size() > 0) {
            setCustomer();
        } else {
            fetchCustomers();
        }
        return v;
    }

    private void uiInitialization(View v) {

        //layoutInflater = getLayoutInflater();
        saleOrderStatusLinearLayout = (LinearLayout) v.findViewById(R.id.saleOrderStatusLinearLayout);
        autoCompleteTextViewCustomers = (AutoCompleteTextView) v.findViewById(R.id.edtCustomerName);
        autoCompleteTextViewProducts = (AutoCompleteTextView) v.findViewById(R.id.edtProductName);
        editTextQuantity = (EditText) v.findViewById(R.id.edtQuantity);
        editTextDiscount = (EditText) v.findViewById(R.id.edtDiscount);
        spinnerPaymentType = (Spinner) v.findViewById(R.id.spinnerPaymentType);
        spinnerUnitType = (Spinner) v.findViewById(R.id.spinnerUnitType);
        editTextCash = (EditText) v.findViewById(R.id.edtCashAmount);
        editTextCredit = (EditText) v.findViewById(R.id.edtCreditAmount);
        editTextPrice = (EditText) v.findViewById(R.id.edtPrice);
        Point pointSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(pointSize);
        autoCompleteTextViewCustomers.setDropDownWidth(pointSize.x - 100);
        autoCompleteTextViewProducts.setDropDownWidth(pointSize.x - 100);
        payment_layout = (LinearLayout) v.findViewById(R.id.paymentLayout);
        // textViewCustomer = (TextView) findViewById(R.id.textViewCustomer);
        voiceButton = (Button) v.findViewById(R.id.generateButton);
        voiceButton.setOnClickListener(this);
        newButton = (Button) v.findViewById(R.id.newButton);
        newButton.setOnClickListener(this);
        clearButton = (Button) v.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);
        submitButton = (Button) v.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        autoCompleteTextViewCustomers.setOnTouchListener(this);
        autoCompleteTextViewProducts.setOnTouchListener(this);
        editTextQuantity.setOnTouchListener(this);
        editTextDiscount.setOnTouchListener(this);


        autoCompleteTextViewCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < customerModels.size(); i++) {
                    if (customerModels.get(i).getName().equals(selected)) {
                        buyerId = customerModels.get(i).getBuyerId() + "";
                        buyerName = customerModels.get(i).getName() + "";
                        i = customerModels.size();
                    }
                }
                autoCompleteTextViewProducts.requestFocus();

                Toast.makeText(getActivity(), buyerName, Toast.LENGTH_LONG).show();
            }
        });
        autoCompleteTextViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < productListModels.size(); i++) {
                    if (productListModels.get(i).getName().equals(selected)) {
                        productId = productListModels.get(i).getProductId() + "";
                        productName = productListModels.get(i).getName();
                        sku = productListModels.get(i).getCategory();
                        editTextPrice.setText(productListModels.get(i).getPrice());
                        priceUnit = productListModels.get(i).getPrice();
                        i = productListModels.size();
                    }

                }
                editTextQuantity.requestFocus();
                UnitTypeList = new DataBaseManager(getActivity()).getUnitTypeList(productId, new AppPreferences(getActivity()).getCompanyId());
                spinnerUnitType.setAdapter(new SpinnerUnitTypeAdapter(getActivity(), UnitTypeList));

                Toast.makeText(getActivity(), productName, Toast.LENGTH_LONG).show();
            }
        });

        spinnerUnitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (UnitTypeList.size() > 0) {
                    productUniTypeId = UnitTypeList.get(position).getUnitTypeId();
                    productUniType = UnitTypeList.get(position).getName();
                    Toast.makeText(getActivity(), UnitTypeList.get(position).getUnitTypeId(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void ClearContent() {
        autoCompleteTextViewCustomers.setText("");
        autoCompleteTextViewProducts.setText("");
        editTextPrice.setText("");
        editTextQuantity.setText("");
        editTextDiscount.setText("");
        editTextCash.setText("");
        editTextCredit.setText("");
        spinnerPaymentType.setSelection(0);
        saleOrderStatusLinearLayout.removeAllViews();
        saleOrdersList.clear();
        autoCompleteTextViewCustomers.requestFocus();
    }

    void NewContent() {

        autoCompleteTextViewProducts.setText("");
        editTextPrice.setText("");
        editTextQuantity.setText("");
        editTextDiscount.setText("");
        editTextCash.setText("");
        editTextCredit.setText("");
        spinnerPaymentType.setSelection(0);
        if (saleOrdersList.size() > 0)
            autoCompleteTextViewProducts.requestFocus();

    }

    void SubmitContent() {

        if (!editTextPrice.getText().toString().equals("") & !editTextQuantity.getText().toString().equals("") & !editTextDiscount.getText().toString().equals("")) {
            float price = Float.parseFloat(editTextPrice.getText().toString());
            salesQuantity = Integer.parseInt(editTextQuantity.getText().toString());
            discount = Float.parseFloat(editTextDiscount.getText().toString());
            totalPayableAmount = (price - discount) * salesQuantity;
            addSalesOrderInList();
        } else {
            Toast.makeText(getActivity(), "Please Input Data", Toast.LENGTH_LONG).show();
        }
        // ClearContent();
    }

    void addSalesOrderInList() {
        saleOrdersList.add(new SaleOrder(productName, sku, salesQuantity + "", priceUnit, totalPayableAmount + "", productId, discount + "", productUniTypeId, productUniType, productId, "0"));
        saleOrderStatusLinearLayout.removeAllViews();
        saleOrderStatusLinearLayout
                .addView(getTableWithAllRowsStretchedView(saleOrdersList));
        Toast.makeText(getActivity(), "Sales Success", Toast.LENGTH_LONG).show();

    }

    void submit() {
        // add sales order & sales order details

    }

    void addProductInLocal() {
        Log.e(Data.TAG, "-----1---: ");
        for (int i = 0; i < productListModels.size(); i++) {
            Log.e(Data.TAG, "-----1---: " + i);
            ProductModel p = productListModels.get(i);
            new DataBaseManager(getActivity()).addLocalProduct(p,
                    new AppPreferences(getActivity()).getCompanyId()
            );
        }
        setProduct();

    }

    public View getTableWithAllRowsStretchedView(
            final ArrayList<SaleOrder> saleOrdersList) {
        TextView serialNoTextView;
        TextView productNameTextView;
        TextView QtyTextView;
        TextView categoryTextView;
        TextView costTextView;
        TextView TotalCostTextView;
        TextView TPTextview;
        TableLayout tableLayout;
        tableLayout = new TableLayout(getActivity());
        View view;
        Button unitButton;
        LayoutInflater layoutInflater;
        // /stableLayout.setStretchAllColumns(true);
        layoutInflater = getLayoutInflater();
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setBackgroundColor(Color.parseColor("#d1d2d4"));
        for (int i = 0; i < saleOrdersList.size(); i++) {
            j = i;

            view = layoutInflater.inflate(R.layout.item_summary_table, null);

            serialNoTextView = (TextView) view
                    .findViewById(R.id.serialNOTextView);
            serialNoTextView.setText((i + 1) + "");
            TableRow tableRow = (TableRow) view.findViewById(R.id.tableRow);
            productNameTextView = (TextView) view
                    .findViewById(R.id.productNameTextView);
            if (saleOrdersList.get(i).getProductName().length() > 3) {
                productNameTextView.setText(saleOrdersList.get(i)
                        .getProductName().substring(0, 3));
            } else {
                productNameTextView.setText(saleOrdersList.get(i)
                        .getProductName());
            }

            QtyTextView = (TextView) view.findViewById(R.id.QtyTextView);
            double qt = Double.parseDouble(saleOrdersList.get(i).getQty());
            int qtn = (int) qt;

            QtyTextView.setText(qtn + "");
            categoryTextView = (TextView) view
                    .findViewById(R.id.CategoryTextView);
            categoryTextView.setText(saleOrdersList.get(i).getCategory());

            costTextView = (TextView) view.findViewById(R.id.costTextView);
            costTextView.setText(saleOrdersList.get(i).getPrice());

            TotalCostTextView = (TextView) view
                    .findViewById(R.id.TotalCostTextView);

            TPTextview = (TextView) view.findViewById(R.id.TPTextview);
            TPTextview.setText(saleOrdersList.get(i).getpDiscount());
            // TPTextview
            Log.i(Data.TAG, "BonusProductQty :" + saleOrdersList.get(i).getBonusProductQty());
            if (Double.parseDouble(saleOrdersList.get(i).getTotalPrice()) > 0) {
                TotalCostTextView.setText(RoundConvert.roundDouble(Float.parseFloat(saleOrdersList.get(i).getTotalPrice())));
            } else {
                TotalCostTextView.setText("Bonus");
                TotalCostTextView.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                QtyTextView.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                QtyTextView.setText(saleOrdersList.get(i).getBonusProductQty());
            }
            unitButton = (Button) view.findViewById(R.id.unitTypeEditText);
            unitButton.setText(saleOrdersList.get(i).getUnitTypeName());
            view.setBackgroundColor(Color.DKGRAY);
//			tableRow.addView(view);
            tableRow.setId(1000 + i);
            //tableRow.setOnClickListener(mListener);

            tableLayout.addView(view);
        }

        return tableLayout;
    }

    private void fetchCustomers() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait,Customer Downloading---"); // set message
        progressDialog.show(); // show progress dialog

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<CustomerListModel> call = service.getCustomers("viewCustomers", new AppPreferences(getActivity()).getAreaId(), new AppPreferences(getActivity()).getCompanyId());

        Log.wtf(Data.TAG, call.request().url() + "");
        call.enqueue(new Callback<CustomerListModel>() {
            @Override
            public void onResponse(Call<CustomerListModel> call, Response<CustomerListModel> response) {
                customerModels = response.body().getCustomerModels();
                Log.e(Data.TAG, "customerModels Size: " + customerModels.size());
                if (customerModels.size() > 0) {
                    for (int i = 0; i < customerModels.size(); i++) {
                        Log.e(Data.TAG, "customerModels: " + i);
                        new DataBaseManager(getActivity()).addLocalCustomer(customerModels.get(i), new AppPreferences(getActivity()).getAreaId());
                    }
                    setCustomer();
                    Log.e(Data.TAG, "customerModels Size: " + customerModels.size());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CustomerListModel> call, Throwable t) {
                Log.e(Data.TAG, "ERROR: " + t.toString());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askSpeechInput(String mgs) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                mgs);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.i(Data.TAG, "Voice ERROR: " + a.toString());
        }
    }

    public void containsLocation(String name, String type) {
        if (type.equals("Customer")) {
            for (int i = 0; i < customerModels.size(); i++) {
                if (customerModels.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                    customersListLocalTemp.add(customerModels.get(i));
                }
            }

            Log.i(Data.TAG, "Size: " + customersListLocalTemp.size());
        } else if (type.equals("Product")) {

            for (int i = 0; i < productListModels.size(); i++) {
                if (productListModels.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                    productListLocalTemp.add(productListModels.get(i));
                }
            }

        } else if (type.equals("Payment")) {

            if (name.equalsIgnoreCase("Both")) {
                paymentType = "Both";
                spinnerPaymentType.setSelection(2);

            } else if (name.equalsIgnoreCase("Cash")) {
                paymentType = "Cash";
                spinnerPaymentType.setSelection(0);

            }
            if (name.equalsIgnoreCase("Credit")) {
                paymentType = "Credit";
                spinnerPaymentType.setSelection(1);

            }
        } else if (type.equals("UnitType")) {

            for (int i = 0; i < UnitTypeList.size(); i++) {
                if (UnitTypeList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                    productUniTypeId = UnitTypeList.get(i).getUnitTypeId();
                    spinnerUnitType.setSelection(i);
                    Log.i(Data.TAG, productUniTypeId + ": ******");
                    // productListLocalTemp.add(productListModels.get(i));
                    i = UnitTypeList.size();
                }
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (REQ_CODE_SPEECH_INPUT == 101) {
                // Customer Code 101
                customersListLocalTemp.clear();
                containsLocation(result.get(0), "Customer");
                if (customersListLocalTemp.size() > 0) {
                    if (customersListLocalTemp.size() == 1) {
                        autoCompleteTextViewCustomers.setText(customersListLocalTemp.get(0).getName());
                        // Start Beep audio
                        buyerId = customersListLocalTemp.get(0).getBuyerId() + "";
                        buyerName = customersListLocalTemp.get(0).getName() + "";
                        // Product Code 102
                        REQ_CODE_SPEECH_INPUT = 102;
                        autoCompleteTextViewProducts.requestFocus();
                        askSpeechInput("Please, Speak Product Name");

                    } else {
                        openCustomerAlert();
                    }
                } else {
                    autoCompleteTextViewCustomers.setText("");
                    askSpeechInput("Please Speak Again Customer Name");
                    // Again Open Voice Search
                }
            } else if (REQ_CODE_SPEECH_INPUT == 102) {
                productListLocalTemp.clear();
                String currentString = result.get(0);
                containsLocation(currentString, "Product");
                Log.e(Data.TAG, "productName:" + currentString);

                if (productListLocalTemp.size() > 0) {
                    if (productListLocalTemp.size() == 1) {
                        UnitTypeList.clear();
                        autoCompleteTextViewProducts.setText(productListLocalTemp.get(0).getName());
                        editTextPrice.setText(productListLocalTemp.get(0).getPrice());
                        productId = productListLocalTemp.get(0).getProductId();
                        productName = productListLocalTemp.get(0).getName();
                        sku = productListLocalTemp.get(0).getCategory();
                        priceUnit = productListLocalTemp.get(0).getPrice();
                        productUniTypeId = productListLocalTemp.get(0).getUnitTypeId();
                        productUniType = productListLocalTemp.get(0).getUnitType();

                        UnitTypeList = new DataBaseManager(getActivity()).getUnitTypeList(productId, new AppPreferences(getActivity()).getCompanyId());
                        spinnerUnitType.setAdapter(new SpinnerUnitTypeAdapter(getActivity(), UnitTypeList));

                        for (int i = 0; i < UnitTypeList.size(); i++) {
                            Log.i(Data.TAG, UnitTypeList.get(i).getName());
                        }
                        editTextQuantity.requestFocus();
                        // Product Quantity Code 103
                        REQ_CODE_SPEECH_INPUT = 103;
                        askSpeechInput("Please, Speak Product Quantity");
                    } else {
                        showProductDialog();
                    }
                } else {
                    autoCompleteTextViewProducts.setText("");
                    askSpeechInput("Please Speak Again Product Name");
                }
            } else if (REQ_CODE_SPEECH_INPUT == 103) {
                try {
                    salesQuantity = Integer.parseInt(result.get(0).toString().trim());
                    if (salesQuantity < 1) {
                        askSpeechInput("Please, Speak Again Product Quantity");
                    } else {
                        // Discount Code 104
                        REQ_CODE_SPEECH_INPUT = 104;
                        editTextQuantity.setText(salesQuantity + "");
                        editTextDiscount.requestFocus();
                        askSpeechInput("Please, Speak Product Discount");
                    }
                } catch (Exception e) {
                    Log.v(Data.TAG, "Error 102: " + e.toString());
                    askSpeechInput("Please, Speak Again Product Quantity");
                }


            } else if (REQ_CODE_SPEECH_INPUT == 104) {
                try {
                    discount = Integer.parseInt(result.get(0).toString().trim());
                    Log.v(Data.TAG, "discount: " + discount + "");
                    Log.v(Data.TAG, "quantity: " + salesQuantity + "");
                    if (discount > Double.parseDouble(priceUnit)) {
                        // Show alert for getter then quantit
                        askSpeechInput("Please, Speak Again Product Discount");
                    } else {
                        editTextDiscount.setText(discount + "");
                        double price = Float.parseFloat(priceUnit) - discount;

                        totalPayableAmount = Float.parseFloat(RoundConvert.roundDouble(salesQuantity * price));
                        //  totalPayableAmount = Float.parseFloat(RoundConvert.roundDouble(totalPrice - discount));
                        //  Payment Type Code 105
                        REQ_CODE_SPEECH_INPUT = 105;
                        askSpeechInput("Please, Speak Payment Type");
                    }
                } catch (Exception e) {
                    Log.e(Data.TAG, "Discount Error 103: " + e.toString());
                    askSpeechInput("Please, Speak Again Product Discount");
                }
            } else if (REQ_CODE_SPEECH_INPUT == 105) {
                containsLocation(result.get(0), "Payment");

                if (!paymentType.equals("")) {
                    if (paymentType.equals("Both")) {
                        payment_layout.setVisibility(View.VISIBLE);
                        // Payment Cash Code 106
                        REQ_CODE_SPEECH_INPUT = 108;
                        askSpeechInput("Please, Speak Cash Amount: " + totalPayableAmount);

                    } else {
                        payment_layout.setVisibility(View.GONE);
                        REQ_CODE_SPEECH_INPUT = 106;
                        askSpeechInput("Please, Speak  UnitType");

                    }


                } else {
                    REQ_CODE_SPEECH_INPUT = 105;
                    askSpeechInput("Please, Speak Again Payment Type");
                }
            } else if (REQ_CODE_SPEECH_INPUT == 106) {
                containsLocation(result.get(0), "UnitType");

                if (!productUniTypeId.equals("")) {

                    REQ_CODE_SPEECH_INPUT = 107;
                    askSpeechInput("Please, Speak  New/Submit");

                } else {
                    REQ_CODE_SPEECH_INPUT = 106;
                    askSpeechInput("Please, Speak Again UnitType");
                }
            } else if (REQ_CODE_SPEECH_INPUT == 107) {

                if (result.get(0).equalsIgnoreCase("New")) {
                    addSalesOrderInList();
                    // Add sales order in list and table
                    REQ_CODE_SPEECH_INPUT = 102;
                    askSpeechInput("Please, Speak  New Product");
                } else if (result.get(0).equalsIgnoreCase("Submit")) {
                    // Add sales order in list and table
                    // Submit data in database
                    addSalesOrderInList();

                } else {
                    askSpeechInput("Please, Speak  New/Submit");
                }
                // voice  for cash
            } else if (REQ_CODE_SPEECH_INPUT == 108) {
                try {
                    float cash = Integer.parseInt(result.get(0).toString().trim());
                    if (cash > totalPayableAmount) {
                        // alert
                        REQ_CODE_SPEECH_INPUT = 107;
                        askSpeechInput("Please, Speak again Cash Amount: " + totalPayableAmount);


                    } else {
                        editTextCash.setText(cash + "");
                        editTextCredit.setText((totalPayableAmount - cash) + "");
                        REQ_CODE_SPEECH_INPUT = 106;
                        askSpeechInput("Please, Speak  New/Submit");
                    }
                } catch (Exception e) {
                    REQ_CODE_SPEECH_INPUT = 107;
                    askSpeechInput("Please, Speak again Cash Amount: " + totalPayableAmount);


                }

            }
        }
    }


    private void openCustomerAlert() {

        final Dialog dialog = new Dialog(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_main, null);
        ListView lv = (ListView) view.findViewById(R.id.custom_list);
        // Change MyActivity.this and myListOfItems to your own values
        CustomerListVoiceAdapter clad = new CustomerListVoiceAdapter(getActivity(), customersListLocalTemp);
        lv.setAdapter(clad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                buyerId = customersListLocalTemp.get(i).getBuyerId() + "";
                buyerName = customersListLocalTemp.get(i).getName() + "";
                // editTextCustomerName.setText(buyerName);

                Log.i(Data.TAG, customersListLocalTemp.get(i).getName());
                Log.i(Data.TAG, customersListLocalTemp.get(i).getBuyerId() + "");
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private void showProductDialog() {

        final Dialog dialog = new Dialog(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_main, null);
        ListView lv = (ListView) view.findViewById(R.id.custom_list);
        // Change MyActivity.this and myListOfItems to your own values
        ProductListVoiceAdapter clad = new ProductListVoiceAdapter(getActivity(), productListLocalTemp);
        lv.setAdapter(clad);
        dialog.setContentView(view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UnitTypeList.clear();
                autoCompleteTextViewProducts.setText(productListLocalTemp.get(i).getName());
                productId = productListLocalTemp.get(i).getProductId() + "";
                editTextPrice.setText(productListLocalTemp.get(i).getPrice());
                productName = productListLocalTemp.get(i).getName();
                sku = productListLocalTemp.get(i).getCategory();
                priceUnit = productListLocalTemp.get(i).getPrice();
                productUniTypeId = productListLocalTemp.get(i).getUnitTypeId();
                productUniType = productListLocalTemp.get(i).getUnitType();


               //for (int j = 0; j < UnitTypeList.size(); i++) {
                    Log.i(Data.TAG, "$$$$$$: "+UnitTypeList.size());
                //}

                dialog.dismiss();
                editTextQuantity.requestFocus();
                REQ_CODE_SPEECH_INPUT = 103;
                askSpeechInput("Please, Speak Product Quantity");

                Log.i(Data.TAG, productListLocalTemp.get(i).getName());
                Log.i(Data.TAG, productListLocalTemp.get(i).getProductId() + "");
                UnitTypeList = new DataBaseManager(getActivity()).getUnitTypeList(productId, new AppPreferences(getActivity()).getCompanyId());
                spinnerUnitType.setAdapter(new SpinnerUnitTypeAdapter(getActivity(), UnitTypeList));


            }
        });


        dialog.show();

    }

    private void fetchProduct() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait,Product Downloading---"); // set message
        progressDialog.show(); // show progress dialog

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductListModel> call = service.getProducts("viewProducts", new AppPreferences(getActivity()).getUserId(), new AppPreferences(getActivity()).getCompanyId());
        Log.wtf(Data.TAG, call.request().url() + "");
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                productListModels = response.body().getProductModels();

                if (productListModels.size() > 0) {
                    addProductInLocal();

                    // Log.e(Data.TAG, "productListModels Size: " + productListModels.size());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {
                Log.e(Data.TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setCustomer() {
        customerAutoCompleteAdapter = new CustomerAutoCompleteAdapter(getActivity(),
                R.layout.item_autocomplete, customerModels);
        autoCompleteTextViewCustomers.setAdapter(customerAutoCompleteAdapter);
    }

    void setProduct() {
        adapter = new ProductAutoCompleteAdapter(getActivity(),
                R.layout.item_autocomplete, productListModels);
        autoCompleteTextViewProducts.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.generateButton:
                REQ_CODE_SPEECH_INPUT = 101;
                askSpeechInput("Please, Speak Customer Name");
                break;
            case R.id.submitButton:
                SubmitContent();
                break;

            case R.id.newButton:
                NewContent();
                break;
            case R.id.clearButton:
                ClearContent();
                break;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.edtCustomerName:
                    if (event.getRawX() >= (autoCompleteTextViewCustomers.getRight() - autoCompleteTextViewCustomers.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        REQ_CODE_SPEECH_INPUT = 101;
                        askSpeechInput("Please, Speak Customer Name");
                        Log.i(Data.TAG, "Onclick Customer Voice");
                        return true;
                    }

                    break;
                case R.id.edtProductName:
                    if (event.getRawX() >= (autoCompleteTextViewCustomers.getRight() - autoCompleteTextViewCustomers.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        REQ_CODE_SPEECH_INPUT = 102;
                        askSpeechInput("Please, Speak Product Name");
                        Log.i(Data.TAG, "Onclick Product Voice");
                        return true;
                    }

                    break;
                case R.id.edtQuantity:
                    if (event.getRawX() >= (editTextQuantity.getRight() - editTextQuantity.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        REQ_CODE_SPEECH_INPUT = 103;
                        askSpeechInput("Please, Speak Quantity Name");
                        Log.i(Data.TAG, "Onclick Quantity Voice");
                        return true;
                    }

                    break;
                case R.id.edtDiscount:
                    if (event.getRawX() >= (editTextDiscount.getRight() - editTextDiscount.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        REQ_CODE_SPEECH_INPUT = 104;
                        askSpeechInput("Please, Speak Discount Name");
                        Log.i(Data.TAG, "Onclick Discount Voice");
                        return true;
                    }

                    break;
                case R.id.edtPrice:
                    if (event.getRawX() >= (editTextPrice.getRight() - editTextPrice.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        REQ_CODE_SPEECH_INPUT = 101;
                        askSpeechInput("Please, Speak Price Name");
                        Log.i(Data.TAG, "Onclick Price Voice");
                        return true;
                    }

                    break;
            }

        }

        return false;
    }
}
