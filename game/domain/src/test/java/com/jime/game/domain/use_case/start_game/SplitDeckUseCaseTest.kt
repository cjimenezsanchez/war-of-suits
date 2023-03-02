package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Suit
import com.jime.game.domain.model.Suit.Type.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SplitDeckUseCaseTest {

    private val splitDeckUseCase: SplitDeckInHalfUseCase = SplitDeckInHalfUseCase()

    @Test
    fun `given an empty deck throw and exception`()  {
        val emptyDeck = listOf<Card>()
        val exception = assertThrows(Exception::class.java) {
            splitDeckUseCase(emptyDeck)
        }
        assertEquals("Deck must not be empty", exception.message)
    }

    @Test
    fun `given an odd deck throw and exception`()  {
        val oddDeck = listOf(Card(Suit(HEARTS, 1), 2))
        val exception = assertThrows(Exception::class.java) {
            splitDeckUseCase(oddDeck)
        }
        assertEquals("Deck must be multiple of 2", exception.message)
    }

    @Test
    fun `given a valid deck check if is split in two equal parts`() {
        val validDeck = getValidDeck()
        val splitDeck = splitDeckUseCase(getValidDeck())
        assertEquals(validDeck.size/2, splitDeck.firstHalf.size)
        assertEquals(validDeck.size/2, splitDeck.secondHalf.size)
    }

    private fun getValidDeck(): List<Card> {
        val suits = listOf(Suit(CLUBS), Suit(HEARTS), Suit(DIAMONDS), Suit(SPADES))
        return suits.map { suit -> (2..14).map { value -> Card(suit, value) } }.flatten()
    }

}