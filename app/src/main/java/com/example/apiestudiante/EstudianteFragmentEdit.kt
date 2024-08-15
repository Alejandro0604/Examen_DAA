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
import com.example.apiestudiante.databinding.FragmentEstudianteEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.apiestudiante.data.APIServiceStudent
import com.example.apiestudiante.data.RetrofitBuilder
import com.example.apiestudiante.data.models.DataEstudiante

class EstudianteFragmentEdit : Fragment() {
    private lateinit var binding: FragmentEstudianteEditBinding
    private var estudianteId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEstudianteEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments ?: return
        estudianteId = arguments.getInt("estudiante_id")
        loadEstudiante(estudianteId)

        binding.btnActualizarEs.setOnClickListener {
            updateEstudiante()
        }
    }

    private fun updateEstudiante() {
        CoroutineScope(Dispatchers.IO).launch {
            val estudiante = DataEstudiante(
                id = estudianteId,
                nombre = binding.tfNombreEsEd.editText?.text?.trim().toString(),
                edad = binding.tfEdadEsEd.editText?.text?.trim().toString(),
                email = binding.tfEmailEsEd.editText?.text?.trim().toString(),
                direccion = binding.tfDireccionEsEd.editText?.text?.trim().toString(),
                telefono = binding.tfTelefonoEsEd.editText?.text?.trim().toString(),
                carrera = binding.tfCarreraEsEd.editText?.text?.trim().toString(),
                imagen = binding.tfURLImagenEsEd.editText?.text?.trim().toString()
            )

            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIServiceStudent::class.java).updateEstudiante("estudiante", estudiante.id, estudiante)
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

    private fun loadEstudiante(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val apiServiceStudent = retrofit.create(APIServiceStudent::class.java)
                val response = apiServiceStudent.showEstudiante("estudiantes", id)
                if (response.isSuccessful) {
                    val estudiante = response.body()
                    if (estudiante != null) {
                        Log.e("DataEstudiante", "City data received: $estudiante")

                        binding.tfNombreEsEd.editText?.setText(estudiante.nombre)
                        binding.tfEdadEsEd.editText?.setText(estudiante.edad)
                        binding.tfEmailEsEd.editText?.setText(estudiante.email)
                        binding.tfDireccionEsEd.editText?.setText(estudiante.direccion)
                        binding.tfTelefonoEsEd.editText?.setText(estudiante.telefono)
                        binding.tfCarreraEsEd.editText?.setText(estudiante.carrera)
                        binding.tfURLImagenEsEd.editText?.setText(estudiante.imagen)
                    } else {
                        Toast.makeText(context, "No data found for the estudiante", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                Log.e("Error", "Exception: ${ex.message}")
                Toast.makeText(context, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}