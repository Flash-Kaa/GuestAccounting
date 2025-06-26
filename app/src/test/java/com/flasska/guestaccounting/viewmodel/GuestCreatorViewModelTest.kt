package com.flasska.guestaccounting.viewmodel

import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import com.flasska.guestaccounting.presentation.guest_creator.GuestCreatorEvent
import com.flasska.guestaccounting.presentation.guest_creator.GuestCreatorViewModel
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
class GuestCreatorViewModelTest {

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
    fun `WHEN UpdateName is processed THEN update name in state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(createRepository())
        viewModel.processEvent(GuestCreatorEvent.UpdateName("Alice"))
        advanceUntilIdle()

        assertEquals("Alice", viewModel.state.value.name)
    }

    @Test
    fun `WHEN UpdateAge is processed THEN update age in state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(createRepository())
        viewModel.processEvent(GuestCreatorEvent.UpdateAge(30))
        advanceUntilIdle()

        assertEquals(30, viewModel.state.value.age)
    }

    @Test
    fun `WHEN UpdateGender is processed THEN update gender in state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(createRepository())
        viewModel.processEvent(GuestCreatorEvent.UpdateGender(Guest.Gender.MALE))
        advanceUntilIdle()

        assertEquals(Guest.Gender.MALE, viewModel.state.value.gender)
    }

    @Test
    fun `WHEN UpdateSideOfWedding is processed THEN update side in state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(createRepository())
        viewModel.processEvent(GuestCreatorEvent.UpdateSideOfWedding(Guest.SideOfWedding.GROOM))
        advanceUntilIdle()

        assertEquals(Guest.SideOfWedding.GROOM, viewModel.state.value.sideOfWedding)
    }

    @Test
    fun `WHEN all fields are valid THEN buttonEnabled is true`() = runTest(testDispatcher) {
        val viewModel = createViewModel(createRepository())

        viewModel.processEvent(GuestCreatorEvent.UpdateName("Bob"))
        viewModel.processEvent(GuestCreatorEvent.UpdateAge(20))
        advanceUntilIdle()

        assertEquals(true, viewModel.state.value.buttonEnabled)
    }

    @Test
    fun `WHEN Create is processed AND buttonEnabled false THEN addGuest not called`() = runTest(testDispatcher) {
        val repository = createRepository()
        val viewModel = GuestCreatorViewModel(repository)

        viewModel.processEvent(GuestCreatorEvent.UpdateName("Al"))
        viewModel.processEvent(GuestCreatorEvent.UpdateAge(1))
        advanceUntilIdle()

        viewModel.processEvent(GuestCreatorEvent.Create(Table(1, 5, emptyList())))
        advanceUntilIdle()

        verify(repository, never()).addGuest(any(), any())
    }

    private fun createViewModel(repository: GuestAccountingRepository) =
        GuestCreatorViewModel(
            guestAccountingRepository = repository,
            dispatcher = testDispatcher,
        )

    private fun createRepository() = mock<GuestAccountingRepository> {
        doNothing().`when`(it).addGuest(any(), any())
    }
}