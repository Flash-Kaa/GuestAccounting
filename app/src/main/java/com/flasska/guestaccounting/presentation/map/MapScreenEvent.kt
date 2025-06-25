package com.flasska.guestaccounting.presentation.map

import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table

internal sealed interface MapScreenEvent {

    data class DeleteTable(val table: Table) : MapScreenEvent
    data class UpdateDialogState(val type: MapScreenState.DialogState) : MapScreenEvent
    data class DeleteGuest(
        val guest: Guest,
        val table: Table,
    ) : MapScreenEvent
}
