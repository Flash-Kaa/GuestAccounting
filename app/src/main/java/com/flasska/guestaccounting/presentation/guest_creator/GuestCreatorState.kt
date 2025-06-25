package com.flasska.guestaccounting.presentation.guest_creator

import com.flasska.guestaccounting.domain.model.Guest.Gender
import com.flasska.guestaccounting.domain.model.Guest.SideOfWedding

data class GuestCreatorState(
    val name: String = "",
    val age: Int = 20,
    val gender: Gender = Gender.MALE,
    val sideOfWedding: SideOfWedding = SideOfWedding.GROOM,
    val buttonEnabled: Boolean = false,
)
