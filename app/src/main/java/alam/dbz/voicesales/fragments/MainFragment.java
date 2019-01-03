package alam.dbz.voicesales.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import alam.dbz.voicesales.R;

/**
 * Created by abhay on 20/7/17.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    ActionBar customToobar;
    View view1;
    ImageView editImageView;
    ImageView finishEditingImageView;
    Fragment fragment;

    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater l = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = l.inflate(R.layout.tool_bar, null);

        TextView textView = (TextView) v.findViewById(R.id.custom_toolbar_title);
        textView.setText("Voice Sales Application");
        v.findViewById(R.id.edit_image_view).setVisibility(View.GONE);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        CardView cardViewSales = getView().findViewById(R.id.cardViewSales);
        CardView cardViewInvoice = getView().findViewById(R.id.cardViewInvoice);
        CardView cardViewCollection = getView().findViewById(R.id.cardViewCollection);
        CardView cardViewVoucher = getView().findViewById(R.id.cardViewVoucher);
        CardView cardViewReport = getView().findViewById(R.id.cardViewReport);
        CardView cardViewDataUpdate = getView().findViewById(R.id.cardViewDataUpdate);

        cardViewSales.setOnClickListener(this);
        cardViewInvoice.setOnClickListener(this);
        cardViewCollection.setOnClickListener(this);
        cardViewVoucher.setOnClickListener(this);
        cardViewReport.setOnClickListener(this);
        cardViewDataUpdate.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardViewSales:
                fragment = new SalesFragment();
                replaceFragment();
                break;
        }
    }

    void replaceFragment() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment, "Other Fragment").commit();

    }


}
