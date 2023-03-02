package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Game
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StartNewGameUseCaseTest {

    private val initSuitsWeightUseCase = InitSuitsWeightUseCase()
    private val initDeckUseCase = InitDeckUseCase()
    private val splitDeckInHalfUseCase = SplitDeckInHalfUseCase()
    private val startNewGameUseCase =
        StartNewGameUseCase(initSuitsWeightUseCase, initDeckUseCase, splitDeckInHalfUseCase)

    private val namePlayer1 = "Bob"
    private val namePlayer2 = "Martin"

    private lateinit var game: Game

    @Before
    fun setup() {
        game = startNewGameUseCase(namePlayer1, namePlayer2)
    }

    @Test
    fun `given two player names check names are correctly initialized`() {
        assertEquals(namePlayer1, game.player1.name)
        assertEquals(namePlayer2, game.player2.name)
    }

    @Test
    fun `given two player names check players playing piles are correctly initialized`() {
        assertTrue(game.player1.playingPile.isNotEmpty())
        assertTrue(game.player2.playingPile.isNotEmpty())
        assertTrue(game.player1.playingPile != game.player2.playingPile)
    }

    @Test
    fun `given two player names check players discard piles are correctly initialized`() {
        assertEquals(0, game.player1.discardPile.size)
        assertEquals(0, game.player2.discardPile.size)
    }
}