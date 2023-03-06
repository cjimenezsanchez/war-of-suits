package com.jime.game.domain.model

data class Game(
    val player1: Player1,
    val player2: Player2,
    val suitsWeight: List<Suit>,
    val roundWinners: MutableList<Player> = mutableListOf()
)
