package pe.edu.idat.apppatitasidat.repository

import androidx.lifecycle.LiveData
import pe.edu.idat.apppatitasidat.db.dao.PersonaDao
import pe.edu.idat.apppatitasidat.db.entity.PersonaEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PersonaRepository(private val personaDao: PersonaDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    suspend fun insertar(personaEntity: PersonaEntity){
        personaDao.insertar(personaEntity)
    }
    suspend fun actualizar(personaEntity: PersonaEntity){
        personaDao.actualizar(personaEntity)
    }

    suspend fun eliminartodo(){
        personaDao.eliminartodo()
    }
    fun obtener():
            LiveData<PersonaEntity> {
        return personaDao.obtener()
    }
}