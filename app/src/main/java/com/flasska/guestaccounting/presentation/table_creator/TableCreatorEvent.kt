package com.flasska.guestaccounting.presentation.table_creator

sealed interface TableCreatorEvent {
    data object Create : TableCreatorEvent
    data class UpdateCapacity(val value: Int) : TableCreatorEvent
}
