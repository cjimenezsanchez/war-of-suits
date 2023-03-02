package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.Suit
import com.jime.game.domain.model.Suit.Type.DIAMONDS
import com.jime.game.domain.model.Suit.Type.SPADES
import org.junit.Assert.*
import org.junit.Test

class RemovePlayedCardFromPlayerPileUseCaseTest {

    private val removePlayedCardFromPlayerPile = RemovePlayedCardFromPlayerPileUseCase()
    private val card1 = Card(Suit(DIAMONDS, 3), 11)
    private val card2 = Card(Suit(DIAMONDS, 3), 5)
    private val card3 = Card(Suit(DIAMONDS, 3), 4)
    private val playingPile = listOf(card1, card2, card3)

    @Test
    fun `given a played card verify is no longer present in player playing pile`() {
        val player = Player("Jane", playingPile.toMutableList())
        val card = card1
        removePlayedCardFromPlayerPile(player, card)

        assertFalse(player.playingPile.contains(card))
    }

    @Test
    fun `given a played card that doesn't belong to player throw exception`() {
        val player = Player("Jane", playingPile.toMutableList())
        val randomCard = Card(Suit(SPADES, 0), 9)

        assertThrows(NoSuchElementException::class.java) {
            removePlayedCardFromPlayerPile(player, randomCard)
        }
    }
}