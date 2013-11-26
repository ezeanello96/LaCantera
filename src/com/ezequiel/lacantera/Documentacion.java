package com.ezequiel.lacantera;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Documentacion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.documentacion);
		WebView wv = (WebView) findViewById(R.id.wv1);
		wv.loadUrl("file:///android_asset/Documentacion.html");	
	}
}
