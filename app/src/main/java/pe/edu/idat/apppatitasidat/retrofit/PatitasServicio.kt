package pe.edu.idat.apppatitasidat.retrofit


import pe.edu.idat.apppatitasidat.retrofit.request.RequestLogin
import pe.edu.idat.apppatitasidat.retrofit.request.RequestRegistro
import pe.edu.idat.apppatitasidat.retrofit.request.RequestVoluntario
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseMascota
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseRegistro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface PatitasServicio {

    @POST("login.php")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @PUT("persona.php")
    fun registro(@Body requestRegistro: RequestRegistro): Call<ResponseRegistro>

    @GET("mascotaperdida.php")
    fun listarMascota(): Call<List<ResponseMascota>>

    @POST("personavoluntaria.php")
    fun registrarVoluntario(@Body requestVoluntario: RequestVoluntario): Call<ResponseRegistro>
}