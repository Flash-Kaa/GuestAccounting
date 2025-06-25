package com.flasska.guestaccounting.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class TableModelDB(
    @PrimaryKey val number: Int,
    val capacity: Int,
)
