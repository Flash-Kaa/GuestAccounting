package com.flasska.guestaccounting.data.repository

import com.flasska.guestaccounting.data.local.GuestAccountingDao
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class GuestAccountingRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN repository is created THEN tablesFlow initialized from DAO`() = runTest(testDispatcher) {
        val dao = createDao()
        val expectedTables = listOf<Table>() // пусто, т.к. DAO возвращает пустые списки
        val repository = createRepository(dao)
        advanceUntilIdle()

        assertEquals(expectedTables, repository.tablesFlow.value)
        verify(dao).getGuests()
        verify(dao).getTables()
    }

    @Test
    fun `WHEN addTable is called THEN table added and DAO addTable called`() = runTest(testDispatcher) {
        val dao = createDao()
        val repository = createRepository(dao)

        repository.addTable(8)
        advanceUntilIdle()

        val tables = repository.tablesFlow.value
        assertEquals(1, tables.size)
        assertEquals(8, tables.first().capacity)
        verify(dao).addTable(any())
    }

    @Test
    fun `WHEN addGuest is called THEN guest added to table and DAO addGuest called`() = runTest(testDispatcher) {
        val dao = createDao()
        val repository = createRepository(dao)

        repository.addTable(4)
        advanceUntilIdle()

        val guest = Guest("John", 25, Guest.Gender.MALE, Guest.SideOfWedding.GROOM)
        repository.addGuest(guest, 1)
        advanceUntilIdle()

        val table = repository.tablesFlow.value.first()
        assertEquals(1, table.guests.size)
        assertEquals(guest, table.guests.first())
        verify(dao).addGuest(any())
    }

    @Test
    fun `WHEN deleteGuest is called THEN guest removed and DAO deleteGuest called`() = runTest(testDispatcher) {
        val dao = createDao()
        val repository = createRepository(dao)

        repository.addTable(3)
        advanceUntilIdle()

        val guest = Guest("Anna", 30, Guest.Gender.FEMALE, Guest.SideOfWedding.BRIDE)
        repository.addGuest(guest, 1)
        advanceUntilIdle()

        repository.deleteGuest(guest, 1)
        advanceUntilIdle()

        val table = repository.tablesFlow.value.first()
        assertTrue(table.guests.isEmpty())
        verify(dao).deleteGuest(any())
    }

    @Test
    fun `WHEN deleteTable is called THEN table removed and DAO deleteTable and deleteGuest called`() = runTest(testDispatcher) {
        val dao = createDao()
        val repository = createRepository(dao)

        repository.addTable(2)
        advanceUntilIdle()

        val guest = Guest("Mike", 28, Guest.Gender.MALE, Guest.SideOfWedding.GROOM)
        repository.addGuest(guest, 1)
        advanceUntilIdle()

        val table = repository.tablesFlow.value.first()
        repository.deleteTable(table)
        advanceUntilIdle()

        assertTrue(repository.tablesFlow.value.isEmpty())
        verify(dao).deleteTable(any())
        verify(dao).deleteGuest(any())
    }

    private fun createRepository(dao: GuestAccountingDao): GuestAccountingRepositoryImpl =
        GuestAccountingRepositoryImpl(
            databaseDao = dao,
            dispatcher = testDispatcher,
        )

    private fun createDao(): GuestAccountingDao = mock {
        onBlocking { getGuests() } doReturn emptyList()
        onBlocking { getTables() } doReturn emptyList()
        doNothing().`when`(it).addTable(any())
        doNothing().`when`(it).addGuest(any())
        doNothing().`when`(it).deleteGuest(any())
        doNothing().`when`(it).deleteTable(any())
    }
}
