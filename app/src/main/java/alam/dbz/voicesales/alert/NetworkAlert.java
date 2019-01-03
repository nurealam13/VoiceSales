package alam.dbz.voicesales.alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import alam.dbz.voicesales.activities.SplashActivity;

public class NetworkAlert {

	private Context context;
	private AlertDialog dialog;


	public NetworkAlert(Context context) {
		super();
		this.context = context;

	}

	public void setAlertDialog(String mtitle, String mgs) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(mtitle);
		builder.setMessage(mgs);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				
				Intent intent=new Intent(context, SplashActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("NET", 1);
				context.startActivity(intent);
				
				dialog.dismiss();
				
			}

		});

		dialog = builder.create();
		dialog.show();
		 // To show the AlertDialog
		
	}

	
}
