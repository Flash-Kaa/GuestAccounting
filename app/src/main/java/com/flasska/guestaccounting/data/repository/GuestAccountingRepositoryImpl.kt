package com.flasska.guestaccounting.data.repository

import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GuestAccountingRepositoryImpl(
) : GuestAccountingRepository {
    private val _tablesFlow = MutableStateFlow(emptyList<Table>())
    override val tablesFlow = _tablesFlow.asStateFlow()

    override fun addTable(capacity: Int) {
        _tablesFlow.update {
            it + Table(
                number = it.lastOrNull()?.number?.inc() ?: 1,
                capacity = capacity,
            )
        }
    }

    override fun addGuest(guest: Guest, tableNumber: Int) {
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
    }

    override fun deleteTable(table: Table) {
        _tablesFlow.update {
            it.filter { it != table }
        }
    }

    override fun deleteGuest(guest: Guest, tableNumber: Int) {
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
    }
}
