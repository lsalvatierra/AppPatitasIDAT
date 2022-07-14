package pe.edu.idat.apppatitasidat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.apppatitasidat.R
import pe.edu.idat.apppatitasidat.databinding.ActivityLoginBinding
import pe.edu.idat.apppatitasidat.db.entity.PersonaEntity
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidat.utilitarios.AppMensaje
import pe.edu.idat.apppatitasidat.utilitarios.Constantes
import pe.edu.idat.apppatitasidat.utilitarios.SharedPreferencesManager
import pe.edu.idat.apppatitasidat.utilitarios.TipoMensaje
import pe.edu.idat.apppatitasidat.viewmodel.AuthViewModel
import pe.edu.idat.apppatitasidat.viewmodel.PersonaViewModel

class LoginActivity : AppCompatActivity() , View.OnClickListener  {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var authViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //3.2 Realizamos la instancia de ViewModelProvider
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        authViewModel = ViewModelProvider(this)
            .get(AuthViewModel::class.java)
        //4.1 Validamos que exista la preferencia recordardatos
        if(verificarCheckRecordarDatos()){
            //activar checkbox recordar
            binding.chkrecordar.isChecked = true
            binding.etusuario.isEnabled = false
            binding.etpassword.isEnabled = false
            binding.chkrecordar.text = "Quitar el chek para ingresar con otro usuario"
            //obtiene los datos de SQLite y los muestra en los edittext
            personaViewModel.obtener()
                .observe(this, Observer { persona ->
                    // Update the cached copy of the words in the adapter.
                    persona?.let {
                        binding.etusuario.setText(persona.usuario)
                        binding.etpassword.setText(persona.password)
                    }

                })
        }else{
            personaViewModel.eliminartodo()
        }
        //5.1 Crear el evento click Check
        binding.chkrecordar.setOnClickListener(this)
        binding.btnlogin.setOnClickListener(this)
        binding.btnregistrar.setOnClickListener(this)
        authViewModel.responseLogin.observe(this, Observer {
                response -> obtenerDatosLogin(response)
        })
    }

    //3.3 Crear método para validar los valores de SharedPreferences.
    fun verificarCheckRecordarDatos(): Boolean{
        return SharedPreferencesManager().getSomeBooleanValue(Constantes().PREF_RECORDAR)
    }

    fun obtenerDatosLogin(responseLogin: ResponseLogin){
        if(responseLogin.rpta){
            //6.1 Guardar Información en SQLite
            val personaEntity = PersonaEntity(
                responseLogin.idpersona.toInt(), responseLogin.nombres,
                responseLogin.apellidos, responseLogin.email, responseLogin.celular,
                responseLogin.usuario, responseLogin.password, responseLogin.esvoluntario
            )
            if(verificarCheckRecordarDatos()){
                personaViewModel.actualizar(personaEntity)
            }else{
                personaViewModel.insertar(personaEntity)
                if(binding.chkrecordar.isChecked){
                    SharedPreferencesManager().setSomeBooleanValue(Constantes().PREF_RECORDAR, true)
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }else{
            AppMensaje.enviarMensaje(binding.root, responseLogin.mensaje, TipoMensaje.ERROR)
        }
        binding.btnregistrar.isEnabled = true
        binding.btnlogin.isEnabled = true
    }

    fun validarUsuarioPassword() : Boolean{
        var okValidacion = true
        if(binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            okValidacion = false
        } else if(binding.etpassword.text.toString().trim().isEmpty()){
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            okValidacion = false
        }
        return okValidacion
    }

    fun autenticarUsuario(){
        binding.btnregistrar.isEnabled = false
        binding.btnlogin.isEnabled = false
        if(validarUsuarioPassword()){
            authViewModel.autenticarUsuario(binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
        }else{
            AppMensaje.enviarMensaje(binding.root,
                getString(R.string.msgloginincompleto),
                TipoMensaje.ERROR)
            binding.btnregistrar.isEnabled = true
            binding.btnlogin.isEnabled = true
        }
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnlogin -> autenticarUsuario()
            R.id.btnregistrar -> startActivity(Intent(applicationContext,
                RegistroActivity::class.java))
            R.id.chkrecordar -> setearValoresRecordar(vista)
        }
    }

    private fun setearValoresRecordar(vista: View) {
        if(vista is CheckBox){
            val checked = vista.isChecked
            if(!checked){
                if(verificarCheckRecordarDatos()){
                    SharedPreferencesManager().deletePreference(
                        Constantes().PREF_RECORDAR)
                    personaViewModel.eliminartodo()
                    binding.etusuario.isEnabled = true
                    binding.etpassword.isEnabled = true
                    binding.chkrecordar.text = getString(R.string.recordarlogin)
                }
            }
        }
    }
}