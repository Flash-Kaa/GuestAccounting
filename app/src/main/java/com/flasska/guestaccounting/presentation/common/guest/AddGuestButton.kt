package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.flasska.guestaccounting.R

@Composable
fun AddGuestButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_round)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.add_guest_button_padding)),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.add_guest_button_arrangement_space)
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.add_guest_button_content_padding)),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_person_add),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.add_guest_button_icon_size)),
            )

            Text(
                text = stringResource(R.string.add_guest),
                fontSize = 24.sp,
            )
        }
    }
}