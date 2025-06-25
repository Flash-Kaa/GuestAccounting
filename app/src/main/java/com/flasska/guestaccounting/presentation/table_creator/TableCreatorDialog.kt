package com.flasska.guestaccounting.presentation.table_creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flasska.guestaccounting.R

@Composable
fun TableCreatorDialog(
    viewModel: TableCreatorViewModel,
    onDismiss: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(
        state = state,
        processEvent = viewModel::processEvent,
        onDismiss = onDismiss,
    )
}

@Composable
private fun Content(
    state: TableCreatorState,
    processEvent: (TableCreatorEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.create_arrangement_space)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(R.dimen.create_padding))
        ) {
            Text(
                text = stringResource(R.string.create_table),
                fontSize = 32.sp,
            )

            Text(
                text = stringResource(R.string.table_capacity),
                fontSize = 20.sp,
            )

            TextField(
                value = state.capacity.toString(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    val intOrNull = it.toIntOrNull()
                    if (intOrNull != null) {
                        processEvent(TableCreatorEvent.UpdateCapacity(intOrNull))
                    }
                },
            )

            Button(
                enabled = state.createButtonEnabled,
                onClick = {
                    processEvent(TableCreatorEvent.Create)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.add),
                    fontSize = 24.sp,
                )
            }
        }
    }
}
