package com.flasska.guestaccounting.presentation.guest_creator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table

@Composable
fun GuestCreatorDialog(
    table: Table,
    viewModel: GuestCreatorViewModel,
    onDismiss: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(
        state = state,
        processEvent = viewModel::processEvent,
        onDismiss = onDismiss,
        table = table,
    )
}

@Composable
private fun Content(
    table: Table,
    state: GuestCreatorState,
    processEvent: (GuestCreatorEvent) -> Unit,
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
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_round)))
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.create_padding))
        ) {
            Text(
                text = stringResource(R.string.add_guest),
                fontSize = 32.sp,
            )

            NameField(processEvent, state)
            AgeField(processEvent, state)
            GenderChooser(processEvent, state)
            SizeOfWedding(processEvent, state)

            Button(
                enabled = state.buttonEnabled,
                onClick = {
                    processEvent(GuestCreatorEvent.Create(table))
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

@Composable
private fun NameField(
    processEvent: (GuestCreatorEvent) -> Unit,
    state: GuestCreatorState,
) {
    Column {
        Text(
            text = stringResource(R.string.name_input),
            fontSize = 20.sp,
        )

        TextField(
            value = state.name,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                processEvent(GuestCreatorEvent.UpdateName(it))
            },
        )
    }
}

@Composable
private fun AgeField(
    processEvent: (GuestCreatorEvent) -> Unit,
    state: GuestCreatorState,
) {
    Column {
        Text(
            text = stringResource(R.string.age_input),
            fontSize = 20.sp,
        )

        TextField(
            value = state.age.toString(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                val intOrNull = it.toIntOrNull()
                if (intOrNull != null) {
                    processEvent(GuestCreatorEvent.UpdateAge(intOrNull))
                }
            },
        )
    }
}

@Composable
private fun GenderChooser(
    processEvent: (GuestCreatorEvent) -> Unit,
    state: GuestCreatorState,
) {
    Column {
        Text(
            text = stringResource(R.string.choose_gender),
            fontSize = 20.sp,
        )

        RadioButtonLine(
            value = stringResource(R.string.male_gender_short),
            selected = state.gender == Guest.Gender.MALE,
            onClick = {
                processEvent(GuestCreatorEvent.UpdateGender(Guest.Gender.MALE))
            }
        )

        RadioButtonLine(
            value = stringResource(R.string.female_gender_short),
            selected = state.gender == Guest.Gender.FEMALE,
            onClick = {
                processEvent(GuestCreatorEvent.UpdateGender(Guest.Gender.FEMALE))
            }
        )
    }
}

@Composable
fun SizeOfWedding(
    processEvent: (GuestCreatorEvent) -> Unit,
    state: GuestCreatorState,
) {
    Column {
        Text(
            text = stringResource(R.string.choose_size_of_wedding),
            fontSize = 20.sp,
        )

        RadioButtonLine(
            value = stringResource(R.string.grooms),
            selected = state.sideOfWedding == Guest.SideOfWedding.GROOM,
            onClick = {
                processEvent(GuestCreatorEvent.UpdateSideOfWedding(Guest.SideOfWedding.GROOM))
            }
        )

        RadioButtonLine(
            value = stringResource(R.string.brides),
            selected = state.sideOfWedding == Guest.SideOfWedding.BRIDE,
            onClick = {
                processEvent(GuestCreatorEvent.UpdateSideOfWedding(Guest.SideOfWedding.BRIDE))
            }
        )

        RadioButtonLine(
            value = stringResource(R.string.neutrals),
            selected = state.sideOfWedding == Guest.SideOfWedding.NEUTRAL,
            onClick = {
                processEvent(GuestCreatorEvent.UpdateSideOfWedding(Guest.SideOfWedding.NEUTRAL))
            }
        )
    }
}

@Composable
private fun RadioButtonLine(
    value: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(value)
    }
}
