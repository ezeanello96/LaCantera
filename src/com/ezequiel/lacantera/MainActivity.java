package com.ezequiel.lacantera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText dia;
	private EditText hora;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Creamos la tabla solo si no existe
		crearTabla();
		dia = (EditText) findViewById(R.id.et1);
		hora = (EditText) findViewById(R.id.et2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void buscarTurnos(View view) {
		// Si no se han ingresado datos nos mostrara un Toast
		if (dia.getText().toString().isEmpty()
				|| hora.getText().toString().isEmpty()) {
			Toast.makeText(this, "Ingrese dia y hora", Toast.LENGTH_SHORT)
					.show();
		} else {
			// Si hay datos los pasamos a la siguiente actividad
			Intent i = new Intent(this, BuscarTurno.class);
			i.putExtra("dia", dia.getText().toString());
			i.putExtra("hora", hora.getText().toString());
			startActivity(i);
		}
	}

	public void borrarTurnos(View view) {
		// Borramos la tabla con todos los turnos y la volvemos a crear
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
		dialogo1.setTitle("Esta seguro...");
		dialogo1.setMessage("¿Esta seguro que quiere borrar todos los turnos?");
		dialogo1.setCancelable(false);
		dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogo1, int id) {
				aceptar();
			}
		});
		dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogo1, int id) {
			}
		});
		dialogo1.show();
	}

	public void aceptar() {
		try {
			borrarcrearTabla();
			Toast.makeText(this, "Todos los turnos fueron borrados",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, "Problemas con este boton", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void agregarTurno(View view) {
		// Pasamos a la actividad que permite agregar turnos
		Intent i = new Intent(this, AgregarTurno.class);
		startActivity(i);
	}

	public void crearTabla() {
		// Creamos este metodo que nos permite crear la Tabla en la base de
		// datos, solo si no existe
		try {
			AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
					"turnos", null, 1);
			SQLiteDatabase bd = admin.getWritableDatabase();
			admin.onCreate(bd);
		} catch (Exception e) {
			// Si algo sucede y tenemos algun error nos mostrara un Toast
			Toast.makeText(this, "Problemas al crear la tabla",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void borrarcrearTabla() {
		// Creamos un metodo que permite borrar la Tabla
		try {
			AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
					"turnos", null, 1);
			SQLiteDatabase bd = admin.getWritableDatabase();
			admin.onUpgrade(bd, 0, 0);
		} catch (Exception e) {
			// Si hay problemas nos mostrara el siguiente Toast
			Toast.makeText(this, "Problemas al borrar la tabla",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item1:
        	Intent i = new Intent(this, Documentacion.class);
    		startActivity(i);
    		break;
        case R.id.item2:
            finish();
        }
        return true;
    }
	
}
