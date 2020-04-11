package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import Utils.Utils
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main3.*
import java.util.ArrayList

class Main3Activity : AppCompatActivity() {
    var id=0
    var listaID = ArrayList<String>()
    internal lateinit var BaseDatos:BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        var extra = intent.extras

        id=extra!!.getInt("id")
        editID.setText(id.toString())
        editTextDescripcion.setText(extra.getString("descripcion"))
        editTextCaptura.setText(extra.getString("fechaCaptura"))
        editTextEntrega.setText(extra.getString("fechaEntrega"))
        cargarImagenes(editID.text.toString())
        apagarEdits()

        BaseDatos= BaseDatos(this,"practica1",null,1)
        btnVerAct.setOnClickListener {
            cargarImagenes(editID.text.toString())
        }//btnMostrar

        btnActualizar.setOnClickListener {

        }

        btnRegresar.setOnClickListener {
            var intento = Intent(this, Main2Activity::class.java)
            startActivityForResult(intento, 0)
        }

        btnEliminar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¡CUIDADO!")
                .setMessage("¿SEGURO QUE DESEA ELIMINAR LA ACTIVIDAD?")
                .setPositiveButton("Eliminar"){d,i->
                    BorrarAct(editID.text.toString())
                    finish()
                }
                .show()

        }//botonEliminar


    }//onCreate
    fun cargarImagenes(id: String) {

        listaID = ArrayList<String>()
        try {
            var baseDatos = BaseDatos(this, "practica1", null, 1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM EVIDENCIA WHERE IDACTIVIDAD = ?"

            var parametros = arrayOf(id)
            var cursor = select.rawQuery(SQL, parametros)
            if(cursor.count<=0){
                var idEv= ArrayList<String>()
                var idAct= ArrayList<String>()
                var arreglo = ArrayList<Bitmap>()
                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val adapter =  AddImage(this, idEv.toArray(arrayid),
                    idAct.toArray(arrayidAct),
                    arreglo.toArray(array))
                listaImg.adapter = adapter
            }
            if (cursor.count > 0) {
                var bit: Bitmap? = null
                var arreglo = ArrayList<Bitmap>()
                this.listaID = ArrayList<String>()
                var idEv=ArrayList<String>()
                var idAct=ArrayList<String>()
                cursor.moveToFirst()
                var cantidad = cursor.count - 1
                (0..cantidad).forEach {
                    arreglo.add(Utils.getImage(buscarImagen(cursor.getString(0).toInt())!!))
                    listaID.add(cursor.getString(0  ))

                    idEv.add(cursor.getString(0))
                    idAct.add(cursor.getString(1))
                    cursor.moveToNext()

                }
                //CONVERTIR ARRAY LIST A ARRAY
                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val miAdaptador = AddImage(this, idEv.toArray(arrayid),idAct.toArray(arrayidAct),arreglo.toArray(array))

                listaImg.adapter = miAdaptador
                listaImg.setOnItemClickListener { parent, view, position, id ->
                    AlertDialog.Builder(this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar esta evidencia?")
                        .setPositiveButton("Eliminar") { d, i ->
                            BorrarEvi(listaID[position])
                            cargarImagenes(editID.text.toString())
                        }

                        .setNegativeButton("Cancelar") { d, i -> }
                        .show()
                }


            } else {
                mensaje("NO EXISTEN EVIDENCIAS")
            }

            select.close()
            baseDatos.close()


        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }
    fun BorrarEvi(id: String) {
        try {
            var baseDatos = BaseDatos(this, "practica1", null, 1)

            var borrar = baseDatos.writableDatabase
            var SQL = "DELETE FROM EVIDENCIA WHERE IDEVIDENCIA=?"
            var seleccion = arrayOf(id)
            borrar.execSQL(SQL, seleccion)
            mensaje("EVIDENCIA ELIMINADA")
            borrar.close()
            baseDatos.close()

        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }
    fun BorrarAct(id: String) {
        try {
            var baseDatos = BaseDatos(this, "practica1", null, 1)
            var eliminar = baseDatos.writableDatabase
            var SQLEvidencia = "DELETE FROM EVIDENCIA WHERE IDACTIVIDAD = ?"

            var parametros = arrayOf(id)
            eliminar.execSQL(SQLEvidencia, parametros)
            var SQLActividad = "DELETE FROM ACTIVIDAD WHERE IDACTIVIDAD = ?"
            eliminar.execSQL(SQLActividad, parametros)
            mensaje("ACTIVIDAD ELIMINADA")
            eliminar.close()
            baseDatos.close()

        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun buscarImagen(id: Int): ByteArray? {
        var base = BaseDatos(this, "practica1", null, 1)
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

    fun apagarEdits(){
        editID.isFocusable=false
        editTextDescripcion.isFocusable=false
        editTextCaptura.isFocusable=false
        editTextEntrega.isFocusable=false

    }//apagarEdits

}//class
