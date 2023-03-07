package com.jime.game.domain.use_case

import com.jime.game.domain.model.*
import com.jime.game.domain.use_case.util.suits
import org.junit.Assert.assertEquals
import org.junit.Test

class GetGameWinnerUseCaseTest {

    private val getWinner = GetGameWinnerUseCase()
    private val testSuits = suits

    private val emptyPlayer1 = Player1("John", mutableListOf())
    private val emptyPlayer2 = Player2("Mary", mutableListOf())

    private val card11 = Card(suits.random(), 11)
    private val card12 = Card(suits.random(),5)
    private val discardPile = mutableListOf(card11, card12)

    @Test
    fun `when player 1 has more points return player1`() {
        val player1 = emptyPlayer1.copy(discardPile = discardPile)
        val player2 = emptyPlayer2

        val game = Game(player1, player2, testSuits)
        val winner = getWinner(game)

        assertEquals(player1,  winner)
    }

    @Test
    fun `when player 2 has more points return player2`() {
        val player1 = emptyPlayer1
        val player2 = emptyPlayer2.copy(discardPile = discardPile)

        val game = Game(player1, player2, testSuits)
        val winner = getWinner(game)

        assertEquals(player2, winner)
    }

    @Test
    fun `when both players has same points return null`() {
        val player1 = emptyPlayer1.copy(discardPile = discardPile)
        val player2 = emptyPlayer2.copy(discardPile = discardPile)

        val game = Game(player1, player2, testSuits)
        val winner = getWinner(game)

        assertEquals(null, winner)
    }

}