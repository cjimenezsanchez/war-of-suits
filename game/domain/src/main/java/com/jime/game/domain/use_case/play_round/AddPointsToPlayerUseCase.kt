package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.addPoints

class AddPointsToPlayerUseCase {

    operator fun invoke(player: Player, cards: List<Card>) {
        player.addPoints(cards)
    }
}