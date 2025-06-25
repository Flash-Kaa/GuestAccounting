package com.flasska.guestaccounting.presentation.map

import com.flasska.guestaccounting.domain.model.Table

internal data class MapScreenState(
    val tables: List<Table> = emptyList(),
    val bottomSheetState: BottomSheetType = BottomSheetType.None,
) {
    enum class BottomSheetType {
        AddTable,
        AddGuest,
        None,
        ;
    }
}
