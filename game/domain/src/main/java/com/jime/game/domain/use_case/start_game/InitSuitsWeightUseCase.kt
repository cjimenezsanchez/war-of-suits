package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Suit
import com.jime.game.domain.model.Suit.Type.*

class InitSuitsWeightUseCase {

    companion object {
        private const val LOWER_WEIGHT = 0
        private const val HIGHER_WEIGHT = 3
    }

    private val suitsTypes = listOf(CLUBS, HEARTS, DIAMONDS, SPADES)

    operator fun invoke(): List<Suit> {
        val weights = (LOWER_WEIGHT..HIGHER_WEIGHT).shuffled()
        return suitsTypes.zip(weights)
            .map { (type, weight) -> Suit(type, weight) }
            .sortedByDescending { it.weight }
    }
}