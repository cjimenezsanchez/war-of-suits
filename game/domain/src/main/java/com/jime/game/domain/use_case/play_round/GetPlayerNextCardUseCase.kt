package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.nextPlayingCardOrNull

class GetPlayerNextCardUseCase {

    operator fun invoke(player: Player): Card? {
        return player.nextPlayingCardOrNull()
    }
}