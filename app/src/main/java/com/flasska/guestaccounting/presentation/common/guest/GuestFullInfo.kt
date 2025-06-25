package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest
import com.flasska.guestaccounting.presentation.common.swipeWithAction
import com.flasska.guestaccounting.presentation.theme.GuestAccountingTheme
import com.flasska.guestaccounting.utils.getTextValue

@Composable
fun GuestFullInfo(
    guest: Guest,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = AnimatedGuestColorBackground(guest.sideOfWedding)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.guest_full_padding))
            .swipeWithAction(onDelete),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_round)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.guest_full_elevation)
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.guest_full_content_arrangement_space)
            ),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.guest_full_content_padding))
                .height(IntrinsicSize.Max),
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.guest_full_icon_size)),
            )

            VerticalDivider(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(R.dimen.guest_full_divider_width)),
            )

            Column {
                Text(
                    text = stringResource(R.string.guest_name, guest.name),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.guest_full_title_bottom_padding)),
                )

                Text(
                    text = stringResource(R.string.guest_age, guest.age),
                )

                Text(
                    text = stringResource(R.string.guest_gender, guest.gender.getTextValue()),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGuestFullInfo() {
    GuestAccountingTheme {
        Scaffold {
            GuestFullInfo(
                onDelete = {},
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
