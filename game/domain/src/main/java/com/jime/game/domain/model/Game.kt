package com.jime.game.domain.model

data class Game(
    val player1: Player,
    val player2: Player,
    val suitsWeight: List<Suit>
)
