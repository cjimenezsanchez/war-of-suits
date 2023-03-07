package com.jime.game.domain.use_case

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.isFinished

class IsGameFinishedUseCase {

    operator fun invoke(game: Game): Boolean {
        return game.isFinished()
    }

}