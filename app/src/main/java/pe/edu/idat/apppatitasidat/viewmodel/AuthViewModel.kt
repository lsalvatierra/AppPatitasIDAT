package pe.edu.idat.apppatitasidat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.apppatitasidat.repository.AuthRepository
import pe.edu.idat.apppatitasidat.retrofit.request.RequestLogin
import pe.edu.idat.apppatitasidat.retrofit.request.RequestRegistro
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseRegistro

class AuthViewModel : ViewModel() {

    var responseLogin: LiveData<ResponseLogin>
    var responseRegistro: LiveData<ResponseRegistro>
    private var repository = AuthRepository()

    init {
        responseLogin = repository.loginResponse
        responseRegistro = repository.registroResponse
    }

    fun autenticarUsuario(usuario: String, password: String)
    {
        responseLogin = repository.autenticarUsuario(
            RequestLogin(usuario, password)
        )
    }

    fun registrarUsuario(nombres: String, apellidos: String,email: String,
                         celular: String,usuario: String, password: String)
    {
        responseRegistro = repository.registrarUsuario(
            RequestRegistro(nombres, apellidos, email, celular,
            usuario, password)
        )
    }
}