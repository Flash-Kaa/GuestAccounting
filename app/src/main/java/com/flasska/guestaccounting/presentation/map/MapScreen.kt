package com.flasska.guestaccounting.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun MapScreen(
    viewModel: MapViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(
        state = state,
        processEvent = viewModel::processEvent,
    )
}

@Composable
private fun Content(
    state: MapScreenState,
    processEvent: (MapScreenEvent) -> Unit
) {

}
