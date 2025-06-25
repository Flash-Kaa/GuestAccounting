package com.flasska.guestaccounting.data.mapper

import com.flasska.guestaccounting.data.model.GuestModelDB
import com.flasska.guestaccounting.domain.model.Guest

internal fun Guest.toModelDB(tableNumber: Int): GuestModelDB {
    return GuestModelDB(
        age = age,
        name = name,
        gender = Guest.Gender.entries.indexOf(gender),
        sideOfWedding = Guest.SideOfWedding.entries.indexOf(sideOfWedding),
        tableNumber = tableNumber,
    )
}

internal fun GuestModelDB.toModel(): Guest {
    return Guest(
        age = age,
        name = name,
        gender = Guest.Gender.entries.get(gender),
        sideOfWedding = Guest.SideOfWedding.entries.get(sideOfWedding),
    )
}
