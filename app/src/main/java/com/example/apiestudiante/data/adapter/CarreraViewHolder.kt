package com.example.apiestudiante.data.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.apiestudiante.R
import com.example.apiestudiante.data.models.Data

class CarreraViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val btnEdit = binding.btnEdit
    private val btnDelete = binding.btnDel

    fun bind(city: Data, onEditClick: (Data) -> Unit, onDeleteClick: (Data) -> Unit) {
        binding.tvNombre.text = carrera.nombre
        Picasso.get()
            .load(carrera.imagen)
            .placeholder(R.drawable.baseline_flag_circle_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivImage)
        btnEdit.setOnClickListener { onEditClick(city) }
        btnDelete.setOnClickListener { onDeleteClick(city) }

    }


}