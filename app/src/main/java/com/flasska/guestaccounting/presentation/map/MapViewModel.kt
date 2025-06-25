package com.flasska.guestaccounting.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MapViewModel : ViewModel() {

    private val _state = MutableStateFlow(MapScreenState())
    val state = _state.asStateFlow()

    fun processEvent(event: MapScreenEvent) {
        when (event) {
            is MapScreenEvent.DeleteGuest -> deleteGuest(event)
            is MapScreenEvent.DeleteTable -> deleteTable(event)
            is MapScreenEvent.UpdateBottomSheetState -> openBottomSheet(event)
        }
    }

    private fun deleteGuest(event: MapScreenEvent.DeleteGuest) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    it.tables.map { table ->
                        if (event.guest in table.guests) {
                            table.copy(
                                guests = table.guests - event.guest
                            )
                        } else {
                            table
                        }
                    }
                )
            }
        }
    }

    private fun deleteTable(event: MapScreenEvent.DeleteTable) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(it.tables - event.table)
            }
        }
    }

    private fun openBottomSheet(event: MapScreenEvent.UpdateBottomSheetState) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    bottomSheetState = event.type,
                )
            }
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MapViewModel() as T
        }
    }
}
