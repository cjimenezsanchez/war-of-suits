package com.jime.game.domain.use_case

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

class GetWinnerUseCase {

    operator fun invoke(game: Game): Player? {
        return game.winner()
    }

}