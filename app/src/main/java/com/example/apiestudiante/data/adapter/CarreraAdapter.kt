package com.example.apiestudiante.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiestudiante.R
import com.example.apiestudiante.data.models.Data

class CarreraAdapter(val listaCarreras: MutableList<Data>,
                     private val onEditClick: (Data) -> Unit,
                     private val onDeleteClick: (Data) -> Unit): RecyclerView.Adapter<CarreraViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarreraViewHolder {
        val binding = LayoutInflater.from(parent.context)
        return CarreraViewHolder(binding.inflate(R.layout.itemcarrera, parent, false))
    }

    override fun getItemCount(): Int = listaCarreras.size

    override fun onBindViewHolder(holder: CarreraViewHolder, position: Int) {
        val item = listaCarreras[position]
        holder.bind(item, onEditClick, onDeleteClick)
    }
}