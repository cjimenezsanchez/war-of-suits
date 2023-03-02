package com.jime.game.domain.model

data class Suit(
    val type: Type,
    val weight: Int = 0
) {

    enum class Type {
        CLUBS, HEARTS, DIAMONDS, SPADES
    }
}
