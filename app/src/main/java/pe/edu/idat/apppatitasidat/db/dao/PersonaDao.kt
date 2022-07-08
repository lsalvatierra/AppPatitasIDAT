package pe.edu.idat.apppatitasidat.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pe.edu.idat.apppatitasidat.db.entity.PersonaEntity

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(persona: PersonaEntity)

    @Update
    suspend fun actualizar(persona: PersonaEntity)

    @Delete
    suspend fun eliminartodo()

    @Query("SELECT * FROM persona LIMIT 1")
    fun obtener(): LiveData<PersonaEntity>

}