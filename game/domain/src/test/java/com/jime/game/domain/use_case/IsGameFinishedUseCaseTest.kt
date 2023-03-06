package com.jime.game.domain.use_case

import com.jime.game.domain.model.*
import com.jime.game.domain.use_case.util.suits
import org.junit.Assert.assertEquals
import org.junit.Test

class IsGameFinishedUseCaseTest {

    private val isGameFinished = IsGameFinishedUseCase()
    private val testSuits: List<Suit> = suits

    private val card11 = Card(suits.random(), 11)
    private val card12 = Card(suits.random(),5)
    private val card13 = Card(suits.random(), 4)

    private val card21 = Card(suits.random(), 7)
    private val card22 = Card(suits.random(), 10)
    private val card23 = Card(suits.random(), 2)
    
    @Test
    fun `given two players with no more cards to play then return game is finished true`() {
        val player1 = Player1("Molly", mutableListOf())
        val player2 = Player2("Dolly", mutableListOf())
        val game = Game(player1, player2, testSuits)

        assertEquals(true, isGameFinished(game))
    }

    @Test
    fun `given two players with still cards to play then return game is finished false`() {
        val player1 = Player1("Molly", mutableListOf(card11, card12, card13))
        val player2 = Player2("Dolly", mutableListOf(card21, card22, card23))
        val game = Game(player1, player2, testSuits)

        assertEquals(false, isGameFinished(game))
    }

}