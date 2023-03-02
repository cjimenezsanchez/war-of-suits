package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.Suit
import com.jime.game.domain.use_case.getPoints
import com.jime.game.domain.use_case.util.suits
import org.junit.Assert.*
import org.junit.Test

class PlayNextRoundUseCaseTest {

    private val getNextPlayerCard = GetPlayerNextCardUseCase()
    private val getHigherCardFromPairOfCards = GetHigherCardFromPairOfCardsUseCase()
    private val addPointsToPlayer = AddPointsToPlayerUseCase()
    private val removePlayedCardFromPlayerPile = RemovePlayedCardFromPlayerPileUseCase()
    private val plaRound = PlayNextRoundUseCase(
        getNextPlayerCard,
        getHigherCardFromPairOfCards,
        addPointsToPlayer,
        removePlayedCardFromPlayerPile
    )

    private val testSuits: List<Suit> = suits

    private val card11 = Card(suits.random(), 11)
    private val card12 = Card(suits.random(),5)
    private val card13 = Card(suits.random(), 4)

    private val card21 = Card(suits.random(), 7)
    private val card22 = Card(suits.random(), 10)
    private val card23 = Card(suits.random(), 2)


    @Test
    fun `player 1 wins round`() {
        val player1 = Player("Mary", mutableListOf(card11, card12, card13))
        val player2 = Player("Nick", mutableListOf(card21, card22, card23))

        val game = Game(player1, player2, testSuits)

        plaRound(game)
        assertEquals(2, player1.getPoints())
        assertEquals(0, player2.getPoints())
        assertFalse(player1.playingPile.contains(card11))
        assertTrue(player1.getPoints() % 2 == 0)
        assertFalse(player2.playingPile.contains(card21))
        assertTrue(player2.getPoints() % 2 == 0)
    }

    @Test
    fun `player 2 wins round`() {
        val player1 = Player("Mary", mutableListOf(card13, card12, card11))
        val player2 = Player("Nick", mutableListOf(card21, card22, card23))

        val game = Game(player1, player2, testSuits)

        plaRound(game)
        assertEquals(0, player1.getPoints())
        assertEquals(2, player2.getPoints())
        assertFalse(player1.playingPile.contains(card13))
        assertTrue(player1.getPoints() % 2 == 0)
        assertFalse(player2.playingPile.contains(card21))
        assertTrue(player2.getPoints() % 2 == 0)
    }

    @Test
    fun `game remains the same when no more cards to play`() {
        val player1 = Player("Mary", mutableListOf())
        val player2 = Player("Nick", mutableListOf(card21, card22, card23))

        val game = Game(player1, player2, testSuits)
        plaRound(game)

        assertEquals(0, player1.getPoints())
        assertEquals(0, player2.getPoints())
        assertTrue(player2.playingPile.containsAll(mutableListOf(card21, card22, card23)))
    }

}