package pe.edu.idat.apppatitasidat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.apppatitasidat.repository.MascotaRepository
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseMascota
import pe.edu.idat.apppatitasidat.retrofit.response.ResponseRegistro

class MascotaViewModel: ViewModel() {

    private var repository = MascotaRepository()
    var responseRegistro: LiveData<ResponseRegistro> = repository.responseRegistro

    fun listarMascotas(): LiveData<List<ResponseMascota>>
    {
        return repository.listarMascotas()
    }

    fun registrarVoluntario(idPersona : Int) {
        responseRegistro = repository.registrarVoluntario(idPersona)
    }

}