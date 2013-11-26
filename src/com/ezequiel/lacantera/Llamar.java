package com.ezequiel.lacantera;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.QuickContactBadge;

public class Llamar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.llamar);
		QuickContactBadge badgeMedium = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
		badgeMedium.assignContactFromPhone("3516773777", true);
		badgeMedium.setImageResource(R.drawable.ic_launcher);
	}

	public void llamar(View view) {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:3516773777"));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("Llamada a La Cantera", "Error en la llamada", e);
		}
	}

	public void finalizar(View view) {
		finish();
	}
}
