package alam.dbz.voicesales.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.models.UnitType;

public class SpinnerUnitTypeAdapter extends BaseAdapter {
	private ArrayList<UnitType> unitTypes;
	private LayoutInflater inflater;
	private Context context;

	public SpinnerUnitTypeAdapter(Context context, ArrayList<UnitType> unitTypes) {
		this.unitTypes = unitTypes;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return unitTypes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = convertView;
		view = inflater.inflate(R.layout.spinner_item, null);
		TextView title = (TextView) view.findViewById(R.id.spinnerTarget);

		title.setText(unitTypes.get(position).getName());
		return view;
	}

}
