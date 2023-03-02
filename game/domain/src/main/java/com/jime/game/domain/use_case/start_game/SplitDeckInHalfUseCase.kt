package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Card

class SplitDeckInHalfUseCase {

    operator fun invoke(fullDeck: List<Card>): SplitDeck {
        if (fullDeck.size % 2 != 0) {
            throw Exception("Deck must be multiple of 2")
        }
        if (fullDeck.isEmpty()) {
            throw Exception("Deck must not be empty")
        }

        return SplitDeck(
            firstHalf = fullDeck.subList(0, fullDeck.size / 2),
            secondHalf = fullDeck.takeLast(fullDeck.size / 2)
        )
    }

    data class SplitDeck(val firstHalf: List<Card>,val secondHalf: List<Card>)

}