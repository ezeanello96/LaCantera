package com.ezequiel.lacantera;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
//import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarTurno extends Activity {
	private EditText nombre, hora, dia, cancha;
	private String nombre1, hora1, dia1, cancha1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregarturno);
	}

	public void add(View v) {
		try {
			//Convertimos a String para poder insertarlo en la base de datos
			dia = (EditText) findViewById(R.id.et3);
			dia1 = dia.getText().toString();
			hora = (EditText) findViewById(R.id.et4);
			hora1 = hora.getText().toString();
			nombre = (EditText) findViewById(R.id.et5);
			nombre1 = nombre.getText().toString();
			cancha = (EditText) findViewById(R.id.et6);
			cancha1 = cancha.getText().toString();
		} catch (NumberFormatException ex) {
			//Si ocurre algun error al convertir nos mostrara este Toast
			Toast.makeText(this, "Error de conversión de tipos",Toast.LENGTH_SHORT).show();
		}
		if (dia1.isEmpty()||hora1.isEmpty()||nombre1.isEmpty()||cancha1.isEmpty()){
			Toast.makeText(this, "Complete los espacios en blanco",Toast.LENGTH_SHORT).show();
		}else{
			try {
				//Creamos la conexion con la base de datos
				AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"turnos", null, 1);
				//Abrimos la base de datos en modo de lectura
				SQLiteDatabase bd = admin.getWritableDatabase();
				//Buscamos si existe algun otro turno a la misma hora y dia en la misma cancha
				Cursor fila = bd.rawQuery("select nombre from turnos where hora=" + hora1 + " and dia=" + dia1 + " and cancha="+ cancha1 +"", null);
				if (fila.moveToFirst()){
					//Si existe algun turno nos avisara y nos borrara algunos datos 
					Toast.makeText(this, "Este turno ya existe...", Toast.LENGTH_SHORT).show();
					cancha.setText("");
					hora.setText("");
					dia.setText("");
				}else{
					//Si no existen turnos, lo cargara
					ContentValues registro = new ContentValues();
					//Ingresamos cada dato en su respectiva columna
					registro.put("cancha", cancha1);
					registro.put("hora", hora1);
					registro.put("dia", dia1);
					registro.put("nombre", nombre1);
					bd.insert("turnos", null, registro);
					//Cerramos la base de datos que estaba en modo lectura y escritura
					bd.close();
					cancha.setText("");
					hora.setText("");
					dia.setText("");
					nombre.setText("");
					//Nos avisa que se cargo el turno
					Toast.makeText(this, "Se cargo el turno", Toast.LENGTH_SHORT).show();
				}			
			} catch (Exception e) {	
				//Si hay algun error nos mostrara el siguiente Toast
				Toast.makeText(this, "Error al cargar el turno", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	public void finalizar(View v) {
		//Finalizamos la actividad 
		finish();
	}
}

