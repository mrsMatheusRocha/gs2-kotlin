package br.com.fiap.arenacidada.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ColaboradorDao {
    @Query("SELECT * FROM tb_colaboradores ORDER BY nome ASC")
    fun getAllColaboradores(): Flow<List<ColaboradorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(colaborador: ColaboradorEntity): Long
}