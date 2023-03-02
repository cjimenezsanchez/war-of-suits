package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.use_case.util.suits
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InitDeckUseCaseTest {

    private val deck = InitDeckUseCase().invoke(suits)

    @Test
    fun `given a full deck of cards check if has the correct size`() {
        assertEquals(52, deck.size)
    }

    @Test
    fun `given a full deck of cards check every suit of cards has the correct size`() {
        val suits = deck.groupBy { it.suit }
        suits.forEach {
            assertEquals(13, it.value.size)
        }
    }

    @Test
    fun `given a full deck of cards check every card is different`() {
        assertTrue(deck.distinct().size == 52)
    }

    @Test
    fun `given a full deck of cards check every card value is in the proper range`() {
        deck.forEach { assertTrue( (2..14).contains(it.value)) }
    }
}