package com.jime.game.domain.use_case

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player
import com.jime.game.domain.use_case.play_round.*
import com.jime.game.domain.use_case.start_game.InitDeckUseCase
import com.jime.game.domain.use_case.start_game.InitSuitsWeightUseCase
import com.jime.game.domain.use_case.start_game.SplitDeckInHalfUseCase
import com.jime.game.domain.use_case.start_game.StartNewGameUseCase

fun main() {

    val startNewGame = StartNewGameUseCase(
        InitSuitsWeightUseCase(),
        InitDeckUseCase(),
        SplitDeckInHalfUseCase()
    )

    val game = startNewGame("Magneto", "Professor Xavier")

    val playRound = PlayNextRoundUseCase(
        GetPlayerNextCardUseCase(),
        GetHigherCardFromPairOfCardsUseCase(),
        AddPointsToPlayerUseCase(),
        RemovePlayedCardFromPlayerPileUseCase()
    )

    val isGameFinished = IsGameFinishedUseCase()
    val getWinner = GetWinnerUseCase()

    while (!isGameFinished(game)) {
        playRound(game)
    }

    printWinner(getWinner(game))
}

private fun printWinner(winner: Player?) {
    winner?.let { it ->
        println("${it.name} scored ${it.getPoints()} points")
        println("The winner is: ${it.name}")
        return
    }
    println("Draw!")
}


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

fun Player.nextPlayingCardOrNull(): Card? {
    return playingPile.firstOrNull()
}

fun Player.hasCardsToPlay(): Boolean {
    return playingPile.isNotEmpty()
}

fun Player.removedPlayedCard(card: Card) {
    playingPile.remove(card)
}

fun Player.addPoints(cards: List<Card>) {
    discardPile.addAll(cards)
}

fun Player.getPoints(): Int {
    return discardPile.size
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

