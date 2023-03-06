package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Suit

class InitDeckUseCase {

    companion object {
        private const val CARD_LOWEST_VALUE = 2
        private const val CARD_HIGHEST_VALUE = 2
    }

    operator fun invoke(suits: List<Suit>): List<Card> {
        return suits
            .map { suit -> getCompleteSuitOfCards(suit) }
            .flatten()
            .shuffled()
    }

    private fun getCompleteSuitOfCards(suit: Suit): List<Card> {
        return (CARD_LOWEST_VALUE..CARD_HIGHEST_VALUE).map { value -> Card(suit, value) }
    }

}