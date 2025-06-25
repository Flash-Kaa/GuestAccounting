package com.flasska.guestaccounting.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.flasska.guestaccounting.data.model.GuestModelDB
import com.flasska.guestaccounting.data.model.TableModelDB

@Dao
internal interface GuestAccountingDao {

    @Transaction
    @Query("SELECT * FROM GuestModelDB")
    suspend fun getGuests(): List<GuestModelDB>

    @Transaction
    @Query("SELECT * FROM TableModelDB")
    suspend fun getTables(): List<TableModelDB>

    @Insert
    fun addGuest(guest: GuestModelDB)

    @Insert
    fun addTable(table: TableModelDB)

    @Delete
    fun deleteGuest(guest: GuestModelDB)

    @Delete
    fun deleteTable(table: TableModelDB)
}
