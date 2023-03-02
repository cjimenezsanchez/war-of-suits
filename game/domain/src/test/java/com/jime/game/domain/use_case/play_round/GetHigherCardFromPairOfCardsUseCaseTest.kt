package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Suit
import com.jime.game.domain.model.Suit.Type.*
import org.junit.Assert.assertEquals
import org.junit.Test

class GetHigherCardFromPairOfCardsUseCaseTest {

    private val getHigherCardFromPairOfCardsUseCase = GetHigherCardFromPairOfCardsUseCase()

    @Test
    fun `given a higher card1 than card2 of different suits then return is card1`() {
        val card1 = Card(suit = Suit(DIAMONDS, 0), value = 10)
        val card2 = Card(suit = Suit(HEARTS, 1), value = 4)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)

        assertEquals(card1, higherCard)
    }

    @Test
    fun `given a higher card1 than card2 of same suits then return is card1`() {
        val suit = Suit(DIAMONDS, 0)
        val card1 = Card(suit = suit, value = 13)
        val card2 = Card(suit = suit, value = 5)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)

        assertEquals(card1, higherCard)
    }

    @Test
    fun `given a lower card1 than card2 of different suits check return is card2`() {
        val card1 = Card(suit = Suit(DIAMONDS, 0), value = 2)
        val card2 = Card(suit = Suit(HEARTS, 1), value = 3)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)

        assertEquals(card2, higherCard)
    }

    @Test
    fun `given a lower card1 than card2 of same suits check return is card2`() {
        val suit = Suit(DIAMONDS, 0)
        val card1 = Card(suit = suit, value = 12)
        val card2 = Card(suit = suit, value = 14)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)

        assertEquals(card2, higherCard)
    }

    @Test
    fun `given two cards with same value but card1 with highest suit return is card1`() {
        val cardValue = 10
        val card1 = Card(suit = Suit(DIAMONDS, 3), value = cardValue)
        val card2 = Card(suit = Suit(HEARTS, 0), value = cardValue)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)
        assertEquals(card1, higherCard)
    }

    @Test
    fun `given two cards with same value but card1 with lowest suit return is card2`() {
        val cardValue = 8
        val card1 = Card(suit = Suit(SPADES, 1), cardValue)
        val card2 = Card(suit = Suit(DIAMONDS, 3), cardValue)

        val higherCard = getHigherCardFromPairOfCardsUseCase(card1, card2)
        assertEquals(card2, higherCard)
    }

}