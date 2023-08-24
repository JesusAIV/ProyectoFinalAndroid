package com.grupo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;

public class PersonaDAO {
    private SQLiteDatabase database;
    private MySqliteHelper dbHelper;
    public ArrayList<Persona> lista = new ArrayList<>();

    public PersonaDAO(Context contexto){
        dbHelper = new MySqliteHelper(contexto);
    }

    /**
     * Abre la base de datos en modo escritura para ejecutar consultas
     */
    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void close(){
        dbHelper.close();
    }

    /**
     * Inserta datos en la base de datos
     * @param objPersona
     * @return rowId
     */
    public long Insertar(Persona objPersona){
        ContentValues values = new ContentValues();
        values.put("nombre", objPersona.getNombre());
        values.put("apellido", objPersona.getApellido());
        values.put("dni", objPersona.getDni());

        open();

        long rowId = database.insert("persona", null, values);
        objPersona.setCodigo(Integer.parseInt(String.valueOf((int) rowId)));

        close();

        return rowId;
    }

    public ArrayList<Persona> ListadoPersonas(){
        open();

        String[] columnas = {"codigo", "nombre", "apellido", "dni"};

        Cursor cursor = database.query("persona", columnas, null, null, null, null, null);



        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Persona persona = new Persona();
            persona.setCodigo(Integer.parseInt(String.valueOf(cursor.getInt(0))));
            persona.setNombre(cursor.getString(1));
            persona.setApellido(cursor.getString(2));
            persona.setDni(cursor.getString(3));


            lista.add(persona);


            cursor.moveToNext();
        }

        cursor.close();
        close();

        return lista;
    }
}
