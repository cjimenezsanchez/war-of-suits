package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

class AddRoundWinnerUseCase {

    operator fun invoke(game: Game, player: Player) {
        game.roundWinners.add(player)
    }

}