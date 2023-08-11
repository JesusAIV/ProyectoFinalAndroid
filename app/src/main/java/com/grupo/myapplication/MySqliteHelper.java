package com.grupo.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Conexion a la base de datos con SqlLiteOpenHelper
 * @extends SQLiteOpenHelper
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    static final String NombreTabla = "persona";
    static final String NombreBaseDatos = "BDWilfredo";
    static final int Version = 1;
    static final String Sql =
            "CREATE TABLE " + NombreTabla + " (" +
                    "codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "dni TEXT NOT NULL)";

    /**
     * Constructor de la clase MySqliteHelper
     * @param contexto
     */
    public MySqliteHelper(Context contexto){
        super(contexto, NombreBaseDatos, null, Version);
    }

    /**
     * Ejecuta consulta SQL
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Sql);
    }

    /**
     * Actualiza la base de datos ante cambio de versión
     * @param db
     * @param antiguaversion
     * @param nuevaversion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int antiguaversion, int nuevaversion) {
        db.execSQL("DROP TABLE IF EXISTS " + NombreTabla);
        onCreate(db);
    }
}