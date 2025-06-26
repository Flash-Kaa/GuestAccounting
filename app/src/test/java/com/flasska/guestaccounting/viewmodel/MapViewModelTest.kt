package com.flasska.guestaccounting.viewmodel

import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import com.flasska.guestaccounting.presentation.map.MapScreenEvent
import com.flasska.guestaccounting.presentation.map.MapScreenState
import com.flasska.guestaccounting.presentation.map.MapViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

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
    fun `WHEN tablesFlow emits THEN update state`() = runTest(testDispatcher) {
        val testTable = Table(number = 1, guests = emptyList(), capacity = 4)
        val tablesFlow = MutableSharedFlow<List<Table>>()

        val repository = createRepositoryWithTablesFlow(tablesFlow)
        val viewModel = createViewModel(repository)

        val result = mutableListOf<MapScreenState>()
        val job = launch{ viewModel.state.toList(result) }
        advanceUntilIdle()
        tablesFlow.emit(listOf(testTable))
        advanceUntilIdle()
        job.cancel()

        assertEquals(listOf(testTable), result.last().tables)
    }

    @Test
    fun `WHEN DeleteGuest event is processed THEN call deleteGuest`() = runTest(testDispatcher) {
        val guest = Guest("Test", 16, Guest.Gender.MALE, Guest.SideOfWedding.BRIDE)
        val table = Table(2, 5, listOf(guest))

        val repository = createRepositoryWithTablesFlow(emptyFlow())
        val viewModel = createViewModel(repository)

        viewModel.processEvent(MapScreenEvent.DeleteGuest(guest, table))
        advanceUntilIdle()

        verify(repository).deleteGuest(guest, table.number)
    }

    @Test
    fun `WHEN DeleteTable event is processed THEN call deleteTable`() = runTest(testDispatcher) {
        val table = Table(3, 4, emptyList())

        val repository = createRepositoryWithTablesFlow(emptyFlow())
        val viewModel = createViewModel(repository)

        viewModel.processEvent(MapScreenEvent.DeleteTable(table))
        advanceUntilIdle()

        verify(repository).deleteTable(table)
    }

    private fun createRepositoryWithTablesFlow(
        flow: Flow<List<Table>> = emptyFlow(),
    ): GuestAccountingRepository = mock {
        `when`(it.tablesFlow).thenReturn(flow)
        doNothing().`when`(it).deleteGuest(any(), any())
        doNothing().`when`(it).deleteTable(any())
    }

    private fun createViewModel(repository: GuestAccountingRepository): MapViewModel {
        return MapViewModel(
            guestAccountingRepository = repository,
            dispatcher = testDispatcher,
        )
    }
}
