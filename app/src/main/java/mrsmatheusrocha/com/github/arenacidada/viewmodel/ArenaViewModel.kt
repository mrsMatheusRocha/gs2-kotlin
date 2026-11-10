package br.com.fiap.arenacidada.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.arenacidada.data.CourtDemandDao
import br.com.fiap.arenacidada.data.CourtDemandEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArenaViewModel(private val dao: CourtDemandDao) : ViewModel() {

    val demands: StateFlow<List<CourtDemandEntity>> = dao.getAllDemands()
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
            dao.insert(
                CourtDemandEntity(
                    courtName = courtName.trim(),
                    neighborhood = neighborhood.trim(),
                    category = category.trim(),
                    description = description.trim()
                )
            )
        }
    }

    fun toggleStatus(demand: CourtDemandEntity) {
        viewModelScope.launch {
            dao.update(demand.copy(isResolved = !demand.isResolved))
        }
    }

    fun deleteDemand(demand: CourtDemandEntity) {
        viewModelScope.launch {
            dao.delete(demand)
        }
    }
}

class ArenaViewModelFactory(private val dao: CourtDemandDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArenaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArenaViewModel(dao) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}