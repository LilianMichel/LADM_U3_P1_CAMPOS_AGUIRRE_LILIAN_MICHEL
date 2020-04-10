package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class BaseDatos(context: Context?, nombreBD: String?, cursor: SQLiteDatabase.CursorFactory?, versionBD: Int): SQLiteOpenHelper(context, nombreBD, cursor, versionBD){

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL("CREATE TABLE ACTIVIDAD(IDACTIVIDAD INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPCION VARCHAR(2000), FECHACAPTURA VARCHAR(200), FECHAENTREGA VARCHAR(200))")
            db?.execSQL("CREATE TABLE EVIDENCIA(IDEVIDENCIA INTEGER PRIMARY KEY AUTOINCREMENT, IDACTIVIDAD INTEGER, FOTO BLOB, FOREIGN KEY(IDACTIVIDAD) REFERENCES ACTIVIDAD (IDACTIVIDAD))"
            )
        }catch (err: SQLiteException){

        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}