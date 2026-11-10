package br.com.fiap.arenacidada.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourtDemandDao {
    @Query("SELECT * FROM tb_court_demands ORDER BY isResolved ASC, id DESC")
    fun getAllDemands(): Flow<List<CourtDemandEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(demand: CourtDemandEntity)

    @Update
    suspend fun update(demand: CourtDemandEntity)

    @Delete
    suspend fun delete(demand: CourtDemandEntity)
}