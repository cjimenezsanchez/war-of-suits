package com.jime.game.domain.model

data class Player(
    val name: String,
    val playingPile: MutableList<Card>,
    val discardPile: MutableList<Card> = mutableListOf()
)
