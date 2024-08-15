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
import com.example.apiestudiante.databinding.FragmentCarreraBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.apiestudiante.data.APIService
import com.example.apiestudiante.data.RetrofitBuilder
import com.example.apiestudiante.data.models.Data
import com.example.apiestudiante.databinding.FragmentCarreraEditBinding


class CarreraFragmentEdit : Fragment() {

    private lateinit var binding: FragmentCarreraEditBinding
    private var carreraId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarreraEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments ?: return
        carreraId = arguments.getInt("carrera_id")
        loadCarrera(carreraId)

        binding.btnActualizar.setOnClickListener {
            updateCarrera()
        }
    }

    private fun updateCarrera() {
        CoroutineScope(Dispatchers.IO).launch {
            val carrera = Data(
                id = carreraId,
                nombre = binding.tfNombreCarreraEd.editText?.text?.trim().toString()
            )

            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIService::class.java).updateCarrera("carreras", carrera.id, carrera)
            val response = call.body()
            requireActivity().runOnUiThread {
                if (call.isSuccessful && response != null) {
                    Toast.makeText(context, "Registro actualizado...", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
            private fun loadCarrera(id: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val retrofit = RetrofitBuilder.getRetrofit()
                        val apiService = retrofit.create(APIService::class.java)
                        val response = apiService.showCarrera("carreras", id)
                        if (response.isSuccessful) {
                            val carrera = response.body()
                            if (carrera != null) {
                                Log.e("Data", "Carrera data received: $carrera")

                                // Actualiza la UI con los datos de la ciudad en el hilo principal
                                binding.tfNombreCarreraEd.editText?.setText(carrera.nombre)


                            } else {
                                Toast.makeText(
                                    context,
                                    "No data found for the carrera",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Error: ${response.message()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (ex: Exception) {
                        Log.e("Error", "Exception: ${ex.message}")
                        Toast.makeText(context, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
}