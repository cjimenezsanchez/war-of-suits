package com.jime.game.domain.model

data class Card(
    val suit: Suit,
    val value: Int
)

fun Card.valueAsString(): String {
    return when (value) {
        in 2..10 -> value.toString()
        11 -> "J"
        12 -> "Q"
        13 -> "K"
        14 -> "A"
        else -> ""
    }
}

infix fun Card.isHigherThan(otherCard: Card): Boolean {
    return when {
        this.value > otherCard.value -> true
        this.value < otherCard.value -> false
        else -> {
            when {
                this.suit.weight > otherCard.suit.weight -> true
                else -> false
            }
        }
    }
}