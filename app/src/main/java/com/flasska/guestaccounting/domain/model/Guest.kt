package com.flasska.guestaccounting.domain.model

data class Guest(
    val name: String,
    val age: Int,
    val gender: Gender,
    val sideOfWedding: SideOfWedding,
    val imageUrl: String? = null, // TODO: Можно добавить фото при создании
) {
    enum class Gender {
        MALE,
        FEMALE;
    }

    enum class SideOfWedding {
        BRIDE,
        GROOM,
        NEUTRAL;
    }
}
