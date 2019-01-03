package alam.dbz.voicesales.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.models.CustomerModel;


public class CustomerListVoiceAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<CustomerModel> customersList;
	private ArrayList<CustomerModel> customersDetailsList;

	private LayoutInflater layoutInflater;


	public CustomerListVoiceAdapter(Context context,
                                    ArrayList<CustomerModel> customersList) {

		this.context = context;
		this.customersList = customersList;

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i("LOG", "" + "customerListBaseAdapter ");


	}

	@Override
	public int getCount() {
		return customersList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_search_cusomer,
					null);
			holder = new ViewHolder();

			holder.customerNameTextView = (TextView) convertView
					.findViewById(R.id.customerNameTextView);

			holder.moreImageView = (RelativeLayout) convertView
					.findViewById(R.id.next_layout_first);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}



		holder.customerNameTextView.setText(customersList.get(position)
				.getName());

		return convertView;
	}



	private class ViewHolder {
		ImageView customerImageView;
		RelativeLayout moreImageView;
		TextView customerNameTextView;
	}



}
