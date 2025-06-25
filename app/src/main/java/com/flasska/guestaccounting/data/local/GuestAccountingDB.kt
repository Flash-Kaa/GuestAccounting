package com.flasska.guestaccounting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flasska.guestaccounting.data.model.GuestModelDB
import com.flasska.guestaccounting.data.model.TableModelDB

@Database(entities = [GuestModelDB::class, TableModelDB::class], version = 1)
internal abstract class GuestAccountingDB : RoomDatabase() {
    abstract fun getDao(): GuestAccountingDao
}
