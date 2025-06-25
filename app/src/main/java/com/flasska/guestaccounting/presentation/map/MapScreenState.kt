package com.flasska.guestaccounting.presentation.map

import com.flasska.guestaccounting.domain.model.Table

internal data class MapScreenState(
    val tables: List<Table> = emptyList(),
    val dialogState: DialogState = DialogState.None,
) {
    sealed interface DialogState {
        data object AddTable : DialogState
        data class AddGuest(val table: Table) : DialogState
        data object None : DialogState
    }
}
