package com.example.ladm_u3_p1_campos_aguirre_lilian_michel

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AddImage(private val context: Activity, private val title: Array<String>, private val description: Array<String>, private val imgid: Array<Bitmap>)
    : ArrayAdapter<String>(context, R.layout.addimage, title) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.addimage, null, true)

        val imagen = rowView.findViewById(R.id.imagen) as ImageView
        val titulo = rowView.findViewById(R.id.textViewTit) as TextView
        val subtitulo = rowView.findViewById(R.id.textViewDes) as TextView

        titulo.text = "ID EVIDENCIA: " + title[position]
        subtitulo.text = "ID ACTIVIDAD: " + description[position]
        imagen.setImageBitmap(imgid[position])

        return rowView
    }
}