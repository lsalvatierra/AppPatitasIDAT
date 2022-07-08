package pe.edu.idat.apppatitasidat.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.apppatitasidat.R
import pe.edu.idat.apppatitasidat.databinding.FragmentVoluntarioBinding
import pe.edu.idat.apppatitasidat.db.entity.PersonaEntity
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseRegistro
import pe.edu.idat.apppatitasidat.utilitarios.AppMensaje
import pe.edu.idat.apppatitasidat.utilitarios.TipoMensaje
import pe.edu.idat.apppatitasidat.viewmodel.MascotaViewModel
import pe.edu.idat.apppatitasidat.viewmodel.PersonaViewModel

class VoluntarioFragment : Fragment() {

    private var _binding: FragmentVoluntarioBinding? = null
    private val binding get() = _binding!!
    //Definimos el viewmodel
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var mascotaViewModel: MascotaViewModel
    //Definimos el objeto PersonaEntity
    private lateinit var personaEntity: PersonaEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentVoluntarioBinding.inflate(inflater, container, false)

        //Realizamos la instancia de ViewModelProvider
        val vista = inflater.inflate(R.layout.fragment_voluntario, container, false)
        personaViewModel = ViewModelProvider(requireActivity()).get(PersonaViewModel::class.java)
        mascotaViewModel= ViewModelProvider(requireActivity()).get(MascotaViewModel::class.java)
        personaViewModel.obtener()
            .observe(viewLifecycleOwner, Observer { persona ->
                // Update the cached copy of the words in the adapter.
                persona?.let {
                    if(persona.esvoluntario == "1"){
                        actualizarFormulario()
                    }else{
                        personaEntity = persona
                    }
                }
            })

        binding.btnregvoluntario.setOnClickListener {
            if(binding.chkaceptocondiciones.isChecked){
                binding.btnregvoluntario.isEnabled = false
                mascotaViewModel.registrarVoluntario(personaEntity.id)
            }else{
                AppMensaje.enviarMensaje(binding.root,
                    "Acepte los términos y condiciones para ser voluntario",
                    TipoMensaje.ERROR)
            }
        }
        mascotaViewModel.responseRegistro.observe(viewLifecycleOwner,
            Observer {
                registrarVoluntario(it)
            })
        return binding.root
    }

    private fun registrarVoluntario(responseRegistro: ResponseRegistro){
        if (responseRegistro.rpta) {
            val nuevaPersonaEntity = PersonaEntity(
                personaEntity.id,
                personaEntity.nombres,
                personaEntity.apellidos,
                personaEntity.email,
                personaEntity.celular,
                personaEntity.usuario,
                personaEntity.password,
                "1"
            )
            personaViewModel.actualizar(nuevaPersonaEntity)
            actualizarFormulario()
        }
        AppMensaje.enviarMensaje(binding.root,
            responseRegistro.mensaje,
            TipoMensaje.EXITO)
        binding.btnregvoluntario.isEnabled = true
    }

    /*private fun registrarVoluntario(vista: View) {
        val call: Call<ResponseRegistro> = PatitasCliente
            .retrofitService.registrarVoluntario(RequestVoluntario(personaEntity.id))
        call.enqueue(object : Callback<ResponseRegistro> {
            override fun onResponse(
                call: Call<ResponseRegistro>,
                response: Response<ResponseRegistro>
            ) {
                if (response.body()!!.rpta) {
                    val nuevaPersonaEntity = PersonaEntity(
                            personaEntity.id,
                            personaEntity.nombres,
                            personaEntity.apellidos,
                            personaEntity.email,
                            personaEntity.celular,
                            personaEntity.usuario,
                            personaEntity.password,
                            "1"
                    )
                    personaViewModel.actualizar(nuevaPersonaEntity)
                    actualizarFormulario()
                }
                mostrarMensaje(vista, response.body()!!.mensaje)
                binding.btnregvoluntario.isEnabled = true
            }

            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {
                binding.btnregvoluntario.isEnabled = true
            }

        })
    }*/

    //2. Actualizar controles del formulario
    private fun actualizarFormulario() {
        binding.tvtextovoluntario.visibility = View.GONE
        binding.btnregvoluntario.visibility = View.GONE
        binding.chkaceptocondiciones.visibility = View.GONE
        binding.tvtituvoluntario.text = getString(R.string.valtvesuvoluntario)
    }

}