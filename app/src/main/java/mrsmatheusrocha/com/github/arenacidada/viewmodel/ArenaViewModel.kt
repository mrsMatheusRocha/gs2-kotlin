package br.com.fiap.arenacidada.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.arenacidada.data.CourtDemandDao
import br.com.fiap.arenacidada.data.ColaboradorDao
import br.com.fiap.arenacidada.data.CourtDemandEntity
import br.com.fiap.arenacidada.data.ColaboradorEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArenaViewModel(
    private val demandDao: CourtDemandDao,
    private val colaboradorDao: ColaboradorDao
) : ViewModel() {

    val demands: StateFlow<List<CourtDemandEntity>> = demandDao.getAllDemands()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val colaboradores: StateFlow<List<ColaboradorEntity>> = colaboradorDao.getAllColaboradores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addDemand(
        courtName: String,
        neighborhood: String,
        category: String,
        description: String
    ) {
        viewModelScope.launch {
            demandDao.insert(
                CourtDemandEntity(
                    courtName = courtName.trim(),
                    neighborhood = neighborhood.trim(),
                    category = category.trim(),
                    description = description.trim()
                )
            )
        }
    }

    fun addColaborador(nome: String, especialidade: String, telefone: String) {
        viewModelScope.launch {
            colaboradorDao.insert(
                ColaboradorEntity(
                    nome = nome.trim(),
                    especialidade = especialidade.trim(),
                    telefone = telefone.trim()
                )
            )
        }
    }

    fun allocateColaborador(demand: CourtDemandEntity, colaborador: ColaboradorEntity) {
        viewModelScope.launch {
            demandDao.update(demand.copy(allocatedTo = colaborador.nome))
        }
    }

    fun toggleStatus(demand: CourtDemandEntity) {
        viewModelScope.launch {
            demandDao.update(demand.copy(isResolved = !demand.isResolved))
        }
    }

    fun deleteDemand(demand: CourtDemandEntity) {
        viewModelScope.launch {
            demandDao.delete(demand)
        }
    }
}

class ArenaViewModelFactory(
    private val demandDao: CourtDemandDao,
    private val colaboradorDao: ColaboradorDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArenaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArenaViewModel(demandDao, colaboradorDao) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}