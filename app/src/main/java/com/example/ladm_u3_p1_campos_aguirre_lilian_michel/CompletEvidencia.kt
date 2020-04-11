package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class CompletEvidencia(idActividad: String, foto: ByteArray) {

    var foto = foto
    var idActividad = idActividad
    var idEvidencia = 0
    var error = -1

    val nombreBaseDatos = "practica1"
    var puntero: Context? = null

    fun asignarPuntero(p: Context) {
        puntero = p
    }

    fun insertarImagen(): Boolean {
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("IDACTIVIDAD", idActividad)
            datos.put("FOTO", foto)
            var respuesta = insertar.insert("EVIDENCIA", "IDEVIDENCIA", datos)
            if (respuesta.toInt() == -1) {
                error = 2
                return false
            }
        } catch (e: SQLiteException) {
            error = 1
            return false
        }

        return true
    }

    fun buscarImagen(id: Int): ByteArray? {
        var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
        var buscar = base.readableDatabase
        var columnas = arrayOf("FOTO") // * = todas las columnas
        var cursor = buscar.query("EVIDENCIA",columnas,"IDEVIDENCIA = ?",arrayOf(id.toString()),null,null,null)
        var result: ByteArray? = null
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getBlob(cursor.getColumnIndex("FOTO"))
            } while (cursor.moveToNext())
        }//if
        return result
    }

}//class