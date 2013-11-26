package com.ezequiel.lacantera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BuscarTurno extends Activity {
	String nombre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);
		//Buscamos los TextView para poder cambiar su texto
		TextView tv4 = (TextView) findViewById(R.id.tv4);
		TextView tv6 = (TextView) findViewById(R.id.tv6);
		TextView tv8 = (TextView) findViewById(R.id.tv8);
		//Creamos la conexion con la base de datos y abrimos la tabla en modo lectura y escritura 
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "turnos",null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();
		//Buscamos los datos que le pasamos de la actividad principal
		Bundle bundle = getIntent().getExtras();
		String hora = bundle.getString("hora");
		String dia = bundle.getString("dia");
		//Buscamos el nombre  y el numero de cancha de los turnos con el dia y la hora especificados
		Cursor fila = bd.rawQuery("select cancha,nombre from turnos where hora=" + hora + " and dia=" + dia + "", null);
		if (fila.moveToFirst()) {
			//Si existe algun turno nos retornara un True
			fila.moveToPrevious();
			//Nos volvemos atras asi podemos recorrer las 3 filas y saber a nombre de quien esta y en que cancha esta el turno
			while (fila.moveToNext()) {
				nombre = fila.getString(1);
				//Ponemos en los TextView de cada cancha el nombre de la persona que reservo el turno
				if (fila.getString(0).equals("1")) {
					tv4.setText("Cancha a nombre de: " + nombre);
				}
				if (fila.getString(0).equals("2")) {
					tv6.setText("Cancha a nombre de: " + nombre);
				}
				if (fila.getString(0).equals("3")) {
					tv8.setText("Cancha a nombre de: " + nombre);
				}
			}
		} else
			//Si nos retorna un False la accion .movetoFirst() le decimos que no existen turnos ese dia a esa hora
			Toast.makeText(this, "No existen turnos a ese dia y hora",
					Toast.LENGTH_SHORT).show();
		bd.close();

	}
	
	public void finalizar(View view) {
		//Esta funcion nos permite volver atras y finalizar esta activity
		finish();
	}
	
	public void llamar(View view) {
		//Pasamos a la actividad que nos muestra el contacto para comunicarnos con la cantera
		Intent i = new Intent(this, Llamar.class);
		startActivity(i);
	}
	
	public void borrarTurno() {
		//Borramos un turno si no es de necesidad
		
	}
}
