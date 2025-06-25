package com.flasska.guestaccounting.presentation.guest_creator

import com.flasska.guestaccounting.domain.model.Guest.Gender
import com.flasska.guestaccounting.domain.model.Guest.SideOfWedding
import com.flasska.guestaccounting.domain.model.Table

sealed interface GuestCreatorEvent {
    data class UpdateName(val value: String) : GuestCreatorEvent
    data class UpdateAge(val value: Int) : GuestCreatorEvent
    data class UpdateGender(val value: Gender) : GuestCreatorEvent
    data class UpdateSideOfWedding(val value: SideOfWedding) : GuestCreatorEvent
    data class Create(val value: Table) : GuestCreatorEvent
}
