package com.flasska.guestaccounting.data.repository

import com.flasska.guestaccounting.data.local.GuestAccountingDao
import com.flasska.guestaccounting.data.mapper.toModel
import com.flasska.guestaccounting.data.mapper.toModelDB
import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class GuestAccountingRepositoryImpl(
    private val databaseDao: GuestAccountingDao,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GuestAccountingRepository {
    private val coroutineScope = CoroutineScope(dispatcher)

    private val _tablesFlow = MutableStateFlow(emptyList<Table>())
    override val tablesFlow = _tablesFlow.asStateFlow()

    init {
        coroutineScope.launch {
            val guestsDb = databaseDao.getGuests()
            val tables = databaseDao.getTables().map { it.toModel(guestsDb) }
            _tablesFlow.update { tables }
        }
    }

    override fun addTable(capacity: Int) {
        coroutineScope.launch {
            val table = Table(
                number = tablesFlow.value.lastOrNull()?.number?.inc() ?: 1,
                capacity = capacity,
            )
            _tablesFlow.update { it + table }

            databaseDao.addTable(table.toModelDB())
        }
    }

    override fun addGuest(guest: Guest, tableNumber: Int) {
        coroutineScope.launch {
            _tablesFlow.update {
                it.map { table ->
                    if (table.number == tableNumber) {
                        table.copy(
                            guests = table.guests + guest
                        )
                    } else {
                        table
                    }
                }
            }

            databaseDao.addGuest(guest.toModelDB(tableNumber))
        }
    }

    override fun deleteTable(table: Table) {
        coroutineScope.launch {
            _tablesFlow.update {
                it.filter { it != table }
            }

            table.guests.forEach { tablesGuest ->
                databaseDao.deleteGuest(tablesGuest.toModelDB(table.number))
            }
            databaseDao.deleteTable(table.toModelDB())
        }
    }

    override fun deleteGuest(guest: Guest, tableNumber: Int) {
        coroutineScope.launch {
            _tablesFlow.update {
                it.map { table ->
                    if (table.number == tableNumber) {
                        table.copy(
                            guests = table.guests - guest
                        )
                    } else {
                        table
                    }
                }
            }

            databaseDao.deleteGuest(guest.toModelDB(tableNumber))
        }
    }
}
