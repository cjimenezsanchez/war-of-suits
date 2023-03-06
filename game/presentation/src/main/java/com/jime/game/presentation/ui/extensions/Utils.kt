package com.jime.game.presentation.ui.extensions

import com.jime.game.domain.model.*
import com.jime.game.presentation.R

fun Card.getSuitDrawable(): Int {
    return this.suit.getSuitDrawable()
}

fun Suit.getSuitDrawable(): Int {
    return when (this.type) {
        Suit.Type.HEARTS -> R.drawable.ic_hearts
        Suit.Type.CLUBS -> R.drawable.ic_clubs
        Suit.Type.DIAMONDS -> R.drawable.ic_diamonds
        Suit.Type.SPADES -> R.drawable.ic_spades
    }
}

fun Player?.getColorId(): Int {
    return when (this) {
        is Player1 -> R.color.red
        is Player2 -> R.color.blue
        else -> R.color.dark_grey
    }
}