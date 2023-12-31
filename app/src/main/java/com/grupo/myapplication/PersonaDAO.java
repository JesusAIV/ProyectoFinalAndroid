package com.grupo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PersonaDAO {
    SQLiteDatabase database;
    MySqliteHelper dbHelper;
    ArrayList<Persona> lista = new ArrayList<>();

    public PersonaDAO(Context contexto){
        dbHelper = new MySqliteHelper(contexto);
    }

    /**
     * Abre la base de datos en modo escritura para ejecutar consultas
     */
    public void open(){
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

    public ArrayList<Persona> ListadoGeneral(){
        open();

        String[] columnas = {"codigo", "nombre", "apellido", "dni"};

    }
}
