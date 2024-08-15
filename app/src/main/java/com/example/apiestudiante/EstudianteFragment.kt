package com.example.apiestudiante

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apiestudiante.data.APIServiceStudent
import com.example.apiestudiante.data.RetrofitBuilder
import com.example.apiestudiante.data.models.DataEstudiante
import com.example.apiestudiante.databinding.FragmentEstudianteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class EstudianteFragment : Fragment() {
    private lateinit var binding: FragmentEstudianteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEstudianteBinding.inflate(layoutInflater)
        initEstudiante()
        return binding.root
    }

    private fun initEstudiante() {
        binding.btnGuardar.setOnClickListener {
            saveEstudiante()
        }
    }

    private fun saveEstudiante() {
        val context = requireContext()
        CoroutineScope(Dispatchers.IO).launch {
            val estudiante = DataEstudiante(
                id = 0,
                nombre = binding.tfNombreEs.editText?.text?.trim().toString(),
                edad = binding.tfEdadEs.editText?.text?.trim().toString(),
                email = binding.tfEmailEs.editText?.text?.trim().toString(),
                direccion = binding.tfDireccionEs.editText?.text?.trim().toString(),
                telefono = binding.tfTelefonoEs.editText?.text?.trim().toString(),
                carrera = binding.tfCarreraEs.editText?.text?.trim().toString(),
                imagen = binding.tfURLImagenEs.editText?.text?.trim().toString(),
                created_at = Date().toString(),
                updated_at = Date().toString()
            )
            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIServiceStudent::class.java).saveEstudiante("estudiantes", estudiante)
            val response = call.body()
            requireActivity().runOnUiThread {
                if (call.isSuccessful && response != null) {
                    Toast.makeText(context, "Registro guardado...", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al guardar...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
