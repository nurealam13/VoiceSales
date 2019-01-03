package alam.dbz.voicesales.appmanager;

import android.content.Context;
import android.content.Intent;

public class AppManager {
	private Context context;


	public AppManager(Context context) {
		this.context = context;
	}

	public void SetIntent(Class<?> classname) {
		Intent intent = new Intent(context, classname);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
