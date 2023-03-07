package com.jime.game.domain.use_case

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.winner
import com.jime.game.domain.model.Player

class GetGameWinnerUseCase {

    operator fun invoke(game: Game): Player? {
        return game.winner()
    }

}