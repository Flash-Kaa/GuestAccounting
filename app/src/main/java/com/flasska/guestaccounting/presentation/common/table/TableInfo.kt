package com.flasska.guestaccounting.presentation.common.table

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.domain.model.Table
import com.flasska.guestaccounting.presentation.common.FullnessIndicator
import com.flasska.guestaccounting.presentation.common.guest.AddGuestButton
import com.flasska.guestaccounting.presentation.common.guest.GuestFullInfo
import com.flasska.guestaccounting.presentation.common.guest.GuestShortImage
import com.flasska.guestaccounting.presentation.common.swipeWithAction
import com.flasska.guestaccounting.presentation.theme.GuestAccountingTheme

private const val GUEST_PADDING_RATIO = 0.2f

@Composable
fun TableInfo(
    table: Table,
    onDelete: () -> Unit,
    onDeleteGuest: (Guest) -> Unit,
) {
    var fullInfoOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.swipeWithAction(onDelete),
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_round)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable { fullInfoOpened = fullInfoOpened.not() },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.arrangement_space)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.table_content_padding))
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.table_number, table.number),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (table.guests.isNotEmpty()) {
                    AnimatedVisibility(
                        visible = fullInfoOpened.not(),
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut(),
                    ) {
                        BoxWithConstraints {
                            val size = min(
                                a = maxWidth / table.guests.size - dimensionResource(R.dimen.short_images_space),
                                b = dimensionResource(R.dimen.min_short_images_size)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = dimensionResource(R.dimen.short_images_space)
                                )
                            ) {
                                table.guests.forEach { guest ->
                                    GuestShortImage(
                                        guest = guest,
                                        modifier = Modifier.size(size)
                                    )
                                }
                            }
                        }
                    }

                    FullnessIndicator(
                        fraction = table.guests.size / table.capacity.toFloat(),
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = fullInfoOpened,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            BoxWithConstraints {
                val startPadding = maxWidth * GUEST_PADDING_RATIO

                Column {
                    table.guests.forEach { guest ->
                        GuestFullInfo(
                            guest = guest,
                            onDelete = { onDeleteGuest(guest) },
                            modifier = Modifier.padding(start = startPadding)
                        )
                    }

                    if (table.guests.size < table.capacity) {
                        AddGuestButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(start = startPadding)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTableInfoWithOneGuest() {
    GuestAccountingTheme {
        Scaffold {
            it
            TableInfo(
                onDelete = {},
                onDeleteGuest = {},
                table = Table(
                    number = 1,
                    capacity = 3,
                    guests = listOf(
                        Guest(
                            name = "Name 1234",
                            age = 35,
                            gender = Guest.Gender.FEMALE,
                            sideOfWedding = Guest.SideOfWedding.BRIDE,
                        ),
                    ),
                ),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTableInfoEmpty() {
    GuestAccountingTheme {
        Scaffold {
            it
            TableInfo(
                onDelete = {},
                onDeleteGuest = {},
                table = Table(
                    number = 1,
                    capacity = 3,
                    guests = emptyList(),
                ),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTableInfoFull() {
    GuestAccountingTheme {
        Scaffold {
            it
            TableInfo(
                onDelete = {},
                onDeleteGuest = {},
                table = Table(
                    number = 1,
                    capacity = 3,
                    guests = listOf(
                        Guest(
                            name = "Name 1234",
                            age = 35,
                            gender = Guest.Gender.FEMALE,
                            sideOfWedding = Guest.SideOfWedding.BRIDE,
                        ),
                        Guest(
                            name = "NameMale",
                            age = 19,
                            gender = Guest.Gender.MALE,
                            sideOfWedding = Guest.SideOfWedding.GROOM,
                        ),
                        Guest(
                            name = "NameF",
                            age = 24,
                            gender = Guest.Gender.FEMALE,
                            sideOfWedding = Guest.SideOfWedding.BRIDE,
                        ),
                    ),
                ),
            )
        }
    }
}
