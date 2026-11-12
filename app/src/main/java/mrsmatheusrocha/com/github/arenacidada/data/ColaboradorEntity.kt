package br.com.fiap.arenacidada.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_colaboradores")
data class ColaboradorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val especialidade: String,
    val telefone: String
)