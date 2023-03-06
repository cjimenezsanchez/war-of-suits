package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Game

class PlayNextRoundUseCase (
    val getPlayerNextCard: GetPlayerNextCardUseCase,
    val getHigherCardFromPairOfCards: GetHigherCardFromPairOfCardsUseCase,
    val addPlayerPoints: AddPointsToPlayerUseCase,
    val addRoundWinner: AddRoundWinnerUseCase,
    val removePlayedCardFromPlayerPile: RemovePlayedCardFromPlayerPileUseCase
) {

    operator fun invoke(game: Game) {
        val card1 = getPlayerNextCard(game.player1)
        val card2 = getPlayerNextCard(game.player2)

        if (card1 != null && card2 != null) {
            val higherCard = getHigherCardFromPairOfCards(card1, card2)

            val roundWinner = if (higherCard == card1) {
                game.player1
            } else {
                game.player2
            }

            addPlayerPoints(roundWinner, listOf(card1, card2))
            addRoundWinner(game ,roundWinner)

            removePlayedCardFromPlayerPile(game.player1, card1)
            removePlayedCardFromPlayerPile(game.player2, card2)
        }
    }

}