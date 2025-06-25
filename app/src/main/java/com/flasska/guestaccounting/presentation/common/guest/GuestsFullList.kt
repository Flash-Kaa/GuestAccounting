package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flasska.guestaccounting.domain.model.Guest

private const val GUEST_PADDING_RATIO = 0.2f

@Composable
fun GuestsFullList(
    opened: Boolean,
    guests: List<Guest>,
    maxGuests: Int,
    onAddGuest: () -> Unit,
    onDeleteGuest: (Guest) -> Unit,
) {
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        BoxWithConstraints {
            val startPadding = maxWidth * GUEST_PADDING_RATIO

            Column {
                guests.forEach { guest ->
                    GuestFullInfo(
                        guest = guest,
                        onDelete = { onDeleteGuest(guest) },
                        modifier = Modifier.padding(start = startPadding)
                    )
                }

                if (guests.size < maxGuests) {
                    AddGuestButton(
                        onClick = onAddGuest,
                        modifier = Modifier.padding(start = startPadding)
                    )
                }
            }
        }
    }
}
