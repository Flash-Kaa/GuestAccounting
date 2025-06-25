package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.presentation.theme.GuestAccountingTheme

@Composable
fun GuestShortImage(
    guest: Guest,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = AnimatedGuestColorBackground(guest.sideOfWedding)
    Icon(
        imageVector = Icons.Default.Person,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(dimensionResource(R.dimen.guest_short_padding))
    )
}

@Preview
@Composable
private fun PreviewGuestShortImage() {
    GuestAccountingTheme {
        Scaffold {
            it
            GuestShortImage(
                guest = Guest(
                    name = "Name 1234",
                    age = 35,
                    gender = Guest.Gender.FEMALE,
                    sideOfWedding = Guest.SideOfWedding.BRIDE,
                )
            )
        }
    }
}
