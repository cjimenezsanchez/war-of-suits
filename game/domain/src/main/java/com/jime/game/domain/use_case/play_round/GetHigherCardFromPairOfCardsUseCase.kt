package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.use_case.isHigherThan

class GetHigherCardFromPairOfCardsUseCase {

    operator fun invoke(card1: Card, card2: Card): Card {
        return if (card1 isHigherThan card2) card1 else card2
    }

}