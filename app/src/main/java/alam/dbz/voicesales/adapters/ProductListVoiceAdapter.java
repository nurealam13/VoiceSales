package alam.dbz.voicesales.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import alam.dbz.voicesales.R;
import alam.dbz.voicesales.models.ProductModel;

public class ProductListVoiceAdapter extends BaseAdapter implements Filterable {
	private Context context;
	private ArrayList<ProductModel> productList;
	private LayoutInflater layoutInflater;

	private ImageView customerImageView;
	private RelativeLayout moreImageView;
	private TextView productNameTextView;
	private Valuefilter valuefilter;
	private ArrayList<ProductModel> holdCustomersList;

	public ProductListVoiceAdapter(Context context,
                                   ArrayList<ProductModel> productList) {
		this.context = context;
		this.holdCustomersList = productList;
		this.productList = productList;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {

		return productList.size();
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

		View view = convertView;
		view = layoutInflater.inflate(R.layout.item_search_product, null);

		productNameTextView = (TextView) view
				.findViewById(R.id.productNameTextView);

		moreImageView = (RelativeLayout) view
				.findViewById(R.id.next_layout_first);

		productNameTextView.setText(productList.get(position).getName());

		return view;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub

		if (valuefilter == null)
			valuefilter = new Valuefilter();

		return valuefilter;

	}

	public class Valuefilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			productList = holdCustomersList;
			if (constraint == null || constraint.length() == 0) {
				results.values = productList;
				results.count = productList.size();
			} else {

				ArrayList<ProductModel> newproductList = new ArrayList<ProductModel>();

				for (ProductModel product : productList) {
					if (product.getName().toUpperCase()
							.startsWith(constraint.toString().toUpperCase()))
						newproductList.add(product);
				}

				results.values = newproductList;
				results.count = newproductList.size();

			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				productList = (ArrayList<ProductModel>) results.values;
				notifyDataSetChanged();
			}

		}

	}

}
