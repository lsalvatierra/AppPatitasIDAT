package pe.edu.idat.apppatitasidat.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.apppatitasidat.retrofit.PatitasCliente
import pe.edu.idat.apppatitasidat.retrofit.request.RequestLogin
import pe.edu.idat.apppatitasidat.retrofit.request.RequestRegistro
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseRegistro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    var loginResponse = MutableLiveData<ResponseLogin>()
    var registroResponse = MutableLiveData<ResponseRegistro>()

    fun autenticarUsuario(requestLogin: RequestLogin)
            :MutableLiveData<ResponseLogin>
    {
        val call: Call<ResponseLogin> = PatitasCliente.retrofitService.login(requestLogin)
        call.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                loginResponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("ErrorLogin", t.message.toString())

            }
        })
        return loginResponse
    }

    fun registrarUsuario(requestRegistro: RequestRegistro)
            :MutableLiveData<ResponseRegistro>
    {
        val call: Call<ResponseRegistro> =
            PatitasCliente.retrofitService.registro(requestRegistro)
        call.enqueue(object : Callback<ResponseRegistro> {
            override fun onResponse(call: Call<ResponseRegistro>,
                                    response: Response<ResponseRegistro>) {
                registroResponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {
                Log.e("ErrorRetrofit", t.message.toString())
            }
        })
        return registroResponse
    }

}