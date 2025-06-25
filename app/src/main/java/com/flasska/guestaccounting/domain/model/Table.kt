package com.flasska.guestaccounting.domain.model

data class Table(
    val number: Int,
    val capacity: Int,
    val guests: List<Guest> = emptyList(),
)
