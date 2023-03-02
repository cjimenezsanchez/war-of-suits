package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.Suit
import com.jime.game.domain.use_case.getPoints
import org.junit.Assert.assertTrue
import org.junit.Test

class AddPointsToPlayerUseCaseTest {

    val addPointsToPlayer = AddPointsToPlayerUseCase()

    @Test
    fun `given two cards verify they are added to player's discard pile`() {
        val card1 = Card(Suit(Suit.Type.DIAMONDS, 3), 10)
        val card2 = Card(Suit(Suit.Type.SPADES, 0), 2)
        val points = listOf(card1, card2)

        val player = Player("James", mutableListOf())

        addPointsToPlayer(player, points)

        assertTrue(player.discardPile.containsAll(listOf(card1, card2)))
    }
}