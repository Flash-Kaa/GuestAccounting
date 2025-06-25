package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.min
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest

@Composable
fun GuestsShortList(
    opened: Boolean,
    guests: List<Guest>,
) {
    AnimatedVisibility(
        visible = opened && guests.isNotEmpty(),
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        BoxWithConstraints {
            val size = min(
                a = maxWidth / guests.size - dimensionResource(R.dimen.short_images_space),
                b = dimensionResource(R.dimen.max_short_images_size)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = dimensionResource(R.dimen.short_images_space)
                )
            ) {
                guests.forEach { guest ->
                    GuestShortImage(
                        guest = guest,
                        modifier = Modifier.size(size)
                    )
                }
            }
        }
    }
}
