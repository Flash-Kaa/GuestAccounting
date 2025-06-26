package com.flasska.guestaccounting.presentation.guest_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.domain.model.Guest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestCreatorViewModel(
    private val guestAccountingRepository: GuestAccountingRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): ViewModel() {
    private val _state = MutableStateFlow(GuestCreatorState())
    val state = _state.asStateFlow()

    fun processEvent(event: GuestCreatorEvent) {
        when (event) {
            is GuestCreatorEvent.Create -> create(event)
            is GuestCreatorEvent.UpdateName -> updateName(event)
            is GuestCreatorEvent.UpdateAge -> updateAge(event)
            is GuestCreatorEvent.UpdateGender -> updateGender(event)
            is GuestCreatorEvent.UpdateSideOfWedding -> updateSideOfWedding(event)
        }
    }

    private fun create(event: GuestCreatorEvent.Create) {
        viewModelScope.launch(dispatcher) {
            if (state.value.buttonEnabled) {
                val curValue = state.value
                val guest = Guest(
                    name = curValue.name,
                    age = curValue.age,
                    gender = curValue.gender,
                    sideOfWedding = curValue.sideOfWedding,
                )

                _state.update { GuestCreatorState() }

                guestAccountingRepository.addGuest(
                    guest = guest,
                    tableNumber = event.value.number
                )
            }
        }
    }

    private fun updateName(event: GuestCreatorEvent.UpdateName) {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    name = event.value,
                )
            }
            updateButtonEnabled()
        }
    }

    private fun updateAge(event: GuestCreatorEvent.UpdateAge) {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    age = event.value,
                )
            }
            updateButtonEnabled()
        }
    }

    private fun updateGender(event: GuestCreatorEvent.UpdateGender) {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    gender = event.value,
                )
            }
            updateButtonEnabled()
        }
    }

    private fun updateSideOfWedding(event: GuestCreatorEvent.UpdateSideOfWedding) {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    sideOfWedding = event.value,
                )
            }
            updateButtonEnabled()
        }
    }

    private fun updateButtonEnabled() {
        _state.update {
            it.copy(
                buttonEnabled = it.name.length >= 3 && it.age >= 3,
            )
        }
    }

    class Factory(
        private val guestAccountingRepository: GuestAccountingRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GuestCreatorViewModel(
                guestAccountingRepository = guestAccountingRepository,
            ) as T
        }
    }
}
