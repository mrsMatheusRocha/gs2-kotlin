package br.com.fiap.arenacidada.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_court_demands")
data class CourtDemandEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courtName: String,
    val neighborhood: String,
    val category: String,
    val description: String,
    val isResolved: Boolean = false,
    val allocatedTo: String? = null
)