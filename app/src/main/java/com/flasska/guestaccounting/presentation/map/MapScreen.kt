package com.flasska.guestaccounting.presentation.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.presentation.common.table.AddTableButton
import com.flasska.guestaccounting.presentation.common.table.TableInfo

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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.arrangement_space)),
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.horizontal_padding)),
    ) {
        items(state.tables) { table ->
            TableInfo(
                table = table,
                onDelete = { processEvent(MapScreenEvent.DeleteTable(table)) },
                onDeleteGuest = { guest -> processEvent(MapScreenEvent.DeleteGuest(guest)) }
            )
        }

        item {
            AddTableButton(
                onClick = { /*TODO processEvent(MapScreenEvent.AddTable)*/ }
            )
        }
    }
}
