package com.jime.game.presentation

import com.jime.game.domain.GameHelper
import com.jime.game.domain.model.*

class FakeGameHelper : GameHelper {

    private val suits = listOf(
        Suit(Suit.Type.CLUBS, 0),
        Suit(Suit.Type.HEARTS, 1),
        Suit(Suit.Type.DIAMONDS, 2),
        Suit(Suit.Type.SPADES, 3)
    )

    override fun startGame(playerName1: String, playerName2: String): Game {
        return Game(
            Player1(
                name = playerName1,
                playingPile = mutableListOf(Card(suits.first(), 4), Card(suits.first(), 11))
            ),
            Player2(
                name = playerName2,
                playingPile = mutableListOf(Card(suits.first(), 8), Card(suits.last(), 13))
            ),
            suits,
        )
    }

    override fun getPlayerNextCard(player: Player): Card? {
        return player.playingPile.firstOrNull()
    }

    override fun playNextRound(game: Game) {
        val card1 = game.player1.playingPile.first()
        val card2 = game.player2.playingPile.first()

        val winner = if (card1 isHigherThan card2) {
            game.player1
        } else {
            game.player2
        }

        winner.discardPile.addAll(listOf(card1, card2))
        game.roundWinners.add(winner)
        game.player1.removedPlayedCard(card1)
        game.player2.removedPlayedCard(card2)
    }

    override fun getLastRoundWinner(game: Game): Player? {
        return game.roundWinners.lastOrNull()
    }

    override fun isGameFinished(game: Game): Boolean {
        return game.isFinished()
    }

    override fun getGameWinner(game: Game): Player? {
        return game.winner()
    }

}