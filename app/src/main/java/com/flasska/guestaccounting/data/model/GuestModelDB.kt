package com.flasska.guestaccounting.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class GuestModelDB(
    val name: String,
    val age: Int,
    val gender: Int,
    @ColumnInfo("side_of_wedding") val sideOfWedding: Int,
    @ColumnInfo("table_number") val tableNumber: Int,
    @PrimaryKey val id: String = "$name-$age-$gender-$sideOfWedding-$tableNumber",
)
