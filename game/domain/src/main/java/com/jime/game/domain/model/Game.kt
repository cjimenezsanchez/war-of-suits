package com.jime.game.domain.model

data class Game(
    val player1: Player1,
    val player2: Player2,
    val suitsWeight: List<Suit>,
    val roundWinners: MutableList<Player> = mutableListOf()
)

fun Game.isFinished(): Boolean {
    return !player1.hasCardsToPlay() && !player2.hasCardsToPlay()
}

fun Game.winner(): Player? {
    return when {
        player1.getPoints() > player2.getPoints() -> player1
        player1.getPoints() < player2.getPoints() -> player2
        else -> null
    }
}