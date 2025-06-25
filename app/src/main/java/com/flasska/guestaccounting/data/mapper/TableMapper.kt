package com.flasska.guestaccounting.data.mapper

import com.flasska.guestaccounting.data.model.GuestModelDB
import com.flasska.guestaccounting.data.model.TableModelDB
import com.flasska.guestaccounting.domain.model.Table

internal fun Table.toModelDB(): TableModelDB {
    return TableModelDB(
        number = number,
        capacity = capacity,
    )
}

internal fun TableModelDB.toModel(
    allGuests: List<GuestModelDB>,
): Table {
    return Table(
        number = number,
        capacity = capacity,
        guests = allGuests.filter { it.tableNumber == number }.map(GuestModelDB::toModel)
    )
}
