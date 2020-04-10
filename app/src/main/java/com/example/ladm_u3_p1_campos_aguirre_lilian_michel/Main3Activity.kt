package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {
    var id= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extra = intent.extras

        editTextDescripcion.setText(extra?.getString("descripcion"))
        editTextCaptura.setText(extra?.getString("fechaCaptura"))
        editTextEntrega.setText(extra?.getString("fechaEntrega"))

        id = extra?.getInt("id").toString()

        btnActualizar.setOnClickListener {
            actualizar()
        }

        btnEliminar.setOnClickListener {
            eliminar()
        }

        btnRegresar.setOnClickListener {
            var intento = Intent(this, MainActivity::class.java)
            startActivityForResult(intento,0)

        }

    }
    fun actualizar(){
        var actividadActualizada = Actividad(editTextDescripcion.text.toString(), editTextCaptura.text.toString(), editTextEntrega.text.toString())
        actividadActualizada.id = id.toInt()
        actividadActualizada.asignarPuntero(this)

        if (actividadActualizada.actualizar()==true){
            dialogo("SE ACTUALIZO")
        }else{
            dialogo("ERROR, NO SE PUDO ACTUALIZAR")
        }

        finish()
    }

    fun  dialogo(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }//Dialogo

    fun eliminar(){
        var actividadEliminada = Actividad("","","")
        actividadEliminada.id=id.toInt()
        actividadEliminada.asignarPuntero(this)

        if (actividadEliminada.eliminar()){
            dialogo("SE ELIMINO")
        }else{
            dialogo("NO SE PUDO ELIMINAR")
        }
        finish()

    }


}