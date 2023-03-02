package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Suit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InitSuitsWeightUseCaseTest {

    private val suits = InitSuitsWeightUseCase().invoke()

    @Test
    fun `given a list of suits check length is correct`() {
        assertEquals(4, suits.size )
    }

    @Test
    fun `given a list of suits check that every suit has a different suit types`() {
        assertTrue(suits.map { it.type }.distinct().size == 4)
    }

    @Test
    fun `given a list of suits check that every suit has a different weight`() {
        assertTrue(suits.map { it.weight }.distinct().size == 4)
    }

    @Test
    fun `given a list of suits check every type is present`() {
        val types = suits.map { it.type }
        assertTrue(types.contains(Suit.Type.DIAMONDS))
        assertTrue(types.contains(Suit.Type.SPADES))
        assertTrue(types.contains(Suit.Type.HEARTS))
        assertTrue(types.contains(Suit.Type.SPADES))
    }

    @Test
    fun `given a list of suits check that the weights are in the low higher range`() {
        val weights = suits.map { it.weight }
        assertTrue(weights.max() == 3)
        assertTrue(weights.min() == 0)
    }

}