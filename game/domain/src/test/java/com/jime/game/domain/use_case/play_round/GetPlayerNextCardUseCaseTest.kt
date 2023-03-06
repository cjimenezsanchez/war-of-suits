package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.Player1
import com.jime.game.domain.model.Suit
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPlayerNextCardUseCaseTest {

    private val getPlayerNextCardUseCase = GetPlayerNextCardUseCase()
    private val card1 = Card(Suit(Suit.Type.DIAMONDS, 3), 11)
    private val card2 = Card(Suit(Suit.Type.DIAMONDS, 3), 5)
    private val card3 = Card(Suit(Suit.Type.DIAMONDS, 3), 4)
    private val playingPile = listOf(card1, card2, card3)

    @Test
    fun `given a player with playing card to play verify next card is the first one`() {
        val player = Player1("Random", playingPile.toMutableList())
        val card = playingPile.first()
        assertEquals(card, getPlayerNextCardUseCase(player))
    }

    @Test
    fun `given a player with no more cards to play verify next card is null`(){
        val player = Player1("Another player", mutableListOf())
        assertEquals(null, getPlayerNextCardUseCase(player))
    }
}