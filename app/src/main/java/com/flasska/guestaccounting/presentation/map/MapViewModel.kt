package com.flasska.guestaccounting.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MapViewModel(
    private val guestAccountingRepository: GuestAccountingRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {

    private val _state = MutableStateFlow(MapScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            guestAccountingRepository.tablesFlow.collect { tables ->
                _state.update {
                    it.copy(tables = tables)
                }
            }
        }
    }

    fun processEvent(event: MapScreenEvent) {
        when (event) {
            is MapScreenEvent.DeleteGuest -> deleteGuest(event)
            is MapScreenEvent.DeleteTable -> deleteTable(event)
            is MapScreenEvent.UpdateDialogState -> updateDialogState(event)
        }
    }

    private fun deleteGuest(event: MapScreenEvent.DeleteGuest) {
        viewModelScope.launch(dispatcher) {
            guestAccountingRepository.deleteGuest(
                guest = event.guest,
                tableNumber = event.table.number,
            )
        }
    }

    private fun deleteTable(event: MapScreenEvent.DeleteTable) {
        viewModelScope.launch(dispatcher) {
            guestAccountingRepository.deleteTable(event.table)
        }
    }

    private fun updateDialogState(event: MapScreenEvent.UpdateDialogState) {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    dialogState = event.type,
                )
            }
        }
    }

    class Factory(
        private val guestAccountingRepository: GuestAccountingRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MapViewModel(
                guestAccountingRepository = guestAccountingRepository,
            ) as T
        }
    }
}
