package com.flasska.guestaccounting.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.flasska.guestaccounting.R
import com.flasska.guestaccounting.domain.model.Guest

@Composable
fun Guest.Gender.getTextValue() =
    if (this == Guest.Gender.MALE) stringResource(R.string.male_gender_short)
    else stringResource(R.string.female_gender_short)
