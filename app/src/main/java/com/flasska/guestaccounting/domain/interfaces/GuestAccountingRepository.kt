package com.flasska.guestaccounting.domain.interfaces

import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import kotlinx.coroutines.flow.Flow

interface GuestAccountingRepository {
    val tablesFlow: Flow<List<Table>>

    fun addTable(capacity: Int)
    fun addGuest(guest: Guest, tableNumber: Int)
    fun deleteTable(table: Table)
    fun deleteGuest(guest: Guest, tableNumber: Int)
}
