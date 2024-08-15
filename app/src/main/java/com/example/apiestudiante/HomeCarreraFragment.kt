package com.example.apiestudiante

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiestudiante.data.APIService
import com.example.apiestudiante.data.RetrofitBuilder
import com.example.apiestudiante.data.adapter.CarreraAdapter
import com.example.apiestudiante.data.models.Data
import com.example.apiestudiante.databinding.FragmentCarreraBinding
import com.example.apiestudiante.databinding.FragmentHomeCarreraBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeCarreraFragment : Fragment() {
    private lateinit var binding: FragmentHomeCarreraBinding
    private lateinit var adapter: CarreraAdapter
    private val listado = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeCarreraBinding.inflate(inflater, container, false)

        initViewCa()
        initRecyclerViewCa()
        showCarreras()

        return binding.root
    }

    private fun initViewCa(){
        binding.btnAAdirCarrera.setOnClickListener {
            goNewCarrera()
        }
        binding.tfBuscarCarrera.setOnClickListener {
            val query = binding.etBuscarCarrera.text.toString().trim()
            searchCarreras(query)
        }
    }

    private fun initRecyclerViewCa() {
        adapter = CarreraAdapter(listado, this::onEditClick, this::onDeleteClick)
        binding.RvListaCarrera.layoutManager = LinearLayoutManager(context)
        binding.RvListaCarrera.adapter = adapter
    }

    private fun onDeleteClick(data: Data) {
        val context = requireContext()

        lifecycleScope.launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val call = retrofit.create(APIService::class.java).deleteCarrera("carreras", data.id)
                val response = call.body()
                requireActivity().runOnUiThread {
                    if (call.isSuccessful) {
                        listado.remove(data)
                        adapter.notifyDataSetChanged()
                    } else Toast.makeText(context, "Error al eliminar", Toast.LENGTH_LONG).show()
                    Toast.makeText(context, "Registro eliminado...", Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                Log.e("error", ex.toString())
                Toast.makeText(context, "error ${ex.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onEditClick(data: Data) {
        val bundle = Bundle()
        bundle.putInt("carrera_id", data.id)
        findNavController().navigate(R.id.action_homeCarreraFragment_to_carreraFragmentEdit, bundle)
    }

    private fun showCarreras() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIService::class.java).listaCarreras("carreras")
            val response = call.body()
            requireActivity().runOnUiThread {
                if (call.isSuccessful && response != null) {
                    val listaCarreras = response.data.toMutableList()
                    listado.clear()
                    listado.addAll(listaCarreras)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("listaciu", "Hubo un error")
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
    }

    private fun goNewCarrera() {
        findNavController().navigate(R.id.action_homeCarreraFragment_to_carreraFragment)
    }

    private fun searchCarreras(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val call = retrofit.create(APIService::class.java).listaCarreras("carreras", query)
                val response = call.body()
                requireActivity().runOnUiThread {
                    if (call.isSuccessful && response != null) {
                        Log.e("listacareer", "ok")
                        val listaCarreras = response.data.toMutableList()
                        listado.clear()
                        listado.addAll(listaCarreras)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("listacareer", "Hubo un error")
                        showError()
                    }
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Failure: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}