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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.presentation.common.table.AddTableButton
import com.flasska.guestaccounting.presentation.common.table.TableInfo
import com.flasska.guestaccounting.presentation.guest_creator.GuestCreatorDialog
import com.flasska.guestaccounting.presentation.table_creator.TableCreatorDialog
import com.flasska.guestaccounting.utils.LocalAppComponent

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
        items(
            items = state.tables,
            key = { it.number }
        ) { table ->
            TableInfo(
                table = table,
                onDelete = { processEvent(MapScreenEvent.DeleteTable(table)) },
                onDeleteGuest = { guest -> processEvent(MapScreenEvent.DeleteGuest(guest, table)) },
                onAddGuest = {
                    processEvent(MapScreenEvent.UpdateDialogState(MapScreenState.DialogState.AddGuest(table)))
                },
            )
        }

        item {
            AddTableButton(
                modifier = Modifier.padding(dimensionResource(R.dimen.table_padding)),
                onClick = {
                    processEvent(MapScreenEvent.UpdateDialogState(MapScreenState.DialogState.AddTable))
                }
            )
        }
    }

    if (state.dialogState is MapScreenState.DialogState.AddTable) {
        TableCreatorDialog(
            viewModel = viewModel(factory = LocalAppComponent.provideTableCreatorViewModel()),
            onDismiss = {
                processEvent(MapScreenEvent.UpdateDialogState(MapScreenState.DialogState.None))
            },
        )
    } else if (state.dialogState is MapScreenState.DialogState.AddGuest) {
        GuestCreatorDialog(
            table = state.dialogState.table,
            viewModel = viewModel(factory = LocalAppComponent.provideGuestCreatorViewModel()),
            onDismiss = {
                processEvent(MapScreenEvent.UpdateDialogState(MapScreenState.DialogState.None))
            },
        )
    }
}
