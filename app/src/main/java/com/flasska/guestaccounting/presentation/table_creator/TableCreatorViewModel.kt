package com.flasska.guestaccounting.presentation.table_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TableCreatorViewModel(
    private val guestAccountingRepository: GuestAccountingRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TableCreatorState())
    val state = _state.asStateFlow()

    fun processEvent(event: TableCreatorEvent) {
        when (event) {
            TableCreatorEvent.Create -> create()
            is TableCreatorEvent.UpdateCapacity -> updateCapacity(event)
        }
    }

    private fun create() {
        viewModelScope.launch(Dispatchers.Default) {
            if (state.value.createButtonEnabled) {
                val capacity = state.value.capacity
                _state.update { TableCreatorState() }

                guestAccountingRepository.addTable(capacity)
            }
        }
    }

    private fun updateCapacity(event: TableCreatorEvent.UpdateCapacity) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    capacity = event.value,
                    createButtonEnabled = event.value > 0,
                )
            }
        }
    }

    class Factory(
        private val guestAccountingRepository: GuestAccountingRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TableCreatorViewModel(
                guestAccountingRepository = guestAccountingRepository,
            ) as T
        }
    }
}
