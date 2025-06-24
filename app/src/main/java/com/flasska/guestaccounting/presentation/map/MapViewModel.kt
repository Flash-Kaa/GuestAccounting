package com.flasska.guestaccounting.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class MapViewModel(

) : ViewModel() {

    private val _state = MutableStateFlow(MapScreenState())
    val state = _state.asStateFlow()

    fun processEvent(event: MapScreenEvent) {
//        when (event) {
//
//        }
    }

    class Factory(
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MapViewModel() as T
        }
    }
}
