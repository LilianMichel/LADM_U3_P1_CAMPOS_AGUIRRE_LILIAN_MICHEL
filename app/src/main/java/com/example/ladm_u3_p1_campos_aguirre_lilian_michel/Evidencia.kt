package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import Utils.Utils
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.evidencia.*

class Evidencia : AppCompatActivity() {

    val SELECT_PHOTO = 2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evidencia)
        btnSeleccionarImg.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"

            startActivityForResult(photoPicker, SELECT_PHOTO)
        }//btnSelect

        btnGuardarImg.setOnClickListener {
            val bitmap = (img_select.drawable as BitmapDrawable).bitmap
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("INGRESE ID DE ACTIVIDAD")
            val editText = EditText(this)
            alertDialog.setView((editText))
            alertDialog.setPositiveButton("OK") { dialog, which ->
                var evidencia = CompletEvidencia(editText.text.toString(), Utils.getBytes(bitmap))
                evidencia.asignarPuntero(this)
                var resultado = evidencia.insertarImagen()
                if (resultado == true) {
                    mensaje("EVIDENCIA GUARDADA")
                } else {
                    when (evidencia.error) {
                        1 -> {
                            dialogo("NO SE PUDO CREAR O NO SE CONECTO A LA BASE DE DATOS")
                        }
                        2 -> {
                            dialogo("NO SE PUDO INSERTAR A LA TABLA")
                        }
                    }
                }
            }//setPositive
            alertDialog.show()
        }//btnSave
    }//onCreate
    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun dialogo(s: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("ATENCION").setMessage(s)
            .setPositiveButton("OK") { d, i -> }
            .show()
    }//dialogo


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data !== null) {
            val pickedImage = data.data
            img_select.setImageURI(pickedImage)
        }
    }
}
