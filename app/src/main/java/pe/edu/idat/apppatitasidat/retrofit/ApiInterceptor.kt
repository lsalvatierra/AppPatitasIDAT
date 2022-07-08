package pe.edu.idat.apppatitasidat.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import pe.edu.idat.apppatitasidat.utilitarios.Constantes
import pe.edu.idat.apppatitasidat.utilitarios.SharedPreferencesManager


class ApiInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = SharedPreferencesManager().getSomeStringValue(Constantes().PREF_TOKEN)
        val request = chain.request().newBuilder().addHeader("Authorization",
                "Bearer $token"
        ).build()
        return chain.proceed(request)
    }
}