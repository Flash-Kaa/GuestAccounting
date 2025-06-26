package com.flasska.guestaccounting.viewmodel

import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.presentation.table_creator.TableCreatorEvent
import com.flasska.guestaccounting.presentation.table_creator.TableCreatorViewModel
import com.flasska.guestaccounting.presentation.table_creator.TableCreatorState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class TableCreatorViewModelTest {

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
    fun `WHEN UpdateCapacity is processed THEN update capacity and button enabled in state`() = runTest {
        val viewModel = createViewModel(createRepository())

        viewModel.processEvent(TableCreatorEvent.UpdateCapacity(5))
        advanceUntilIdle()

        assertEquals(5, viewModel.state.value.capacity)
        assertEquals(true, viewModel.state.value.createButtonEnabled)
    }

    @Test
    fun `WHEN UpdateCapacity with zero THEN button enabled is false`() = runTest {
        val viewModel = createViewModel(createRepository())

        viewModel.processEvent(TableCreatorEvent.UpdateCapacity(0))
        advanceUntilIdle()

        assertEquals(0, viewModel.state.value.capacity)
        assertEquals(false, viewModel.state.value.createButtonEnabled)
    }

    @Test
    fun `WHEN Create is processed AND buttonEnabled true THEN call addTable and reset state`() = runTest {
        val repository = createRepository()
        val viewModel = createViewModel(repository)

        viewModel.processEvent(TableCreatorEvent.UpdateCapacity(10))
        advanceUntilIdle()

        viewModel.processEvent(TableCreatorEvent.Create)
        advanceUntilIdle()

        verify(repository).addTable(10)

        val default = TableCreatorState()
        assertEquals(default.capacity, viewModel.state.value.capacity)
        assertEquals(default.createButtonEnabled, viewModel.state.value.createButtonEnabled)
    }

    @Test
    fun `WHEN Create is processed AND buttonEnabled false THEN addTable not called`() = runTest {
        val repository = createRepository()
        val viewModel = createViewModel(repository)

        viewModel.processEvent(TableCreatorEvent.UpdateCapacity(0))
        advanceUntilIdle()

        viewModel.processEvent(TableCreatorEvent.Create)
        advanceUntilIdle()

        verify(repository, never()).addTable(any())
    }

    private fun createViewModel(repository: GuestAccountingRepository) =
        TableCreatorViewModel(guestAccountingRepository = repository)

    private fun createRepository() = mock<GuestAccountingRepository> {
        doNothing().`when`(it).addTable(any())
    }
}
