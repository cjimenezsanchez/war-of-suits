package com.jime.game.domain.use_case.start_game

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

class StartNewGameUseCase(
    private val initSuitsWeightUseCase: InitSuitsWeightUseCase,
    private val initDeckUseCase: InitDeckUseCase,
    private val splitDeckUseCase: SplitDeckInHalfUseCase,
) {

    operator fun invoke(firstPlayerName: String, secondPlayerName: String): Game {

        val suits = initSuitsWeightUseCase()
        val deck = initDeckUseCase(suits)
        val splitDeck = splitDeckUseCase(deck)

        val firstPlayer = Player(
            name = firstPlayerName,
            playingPile = splitDeck.firstHalf.toMutableList()
        )
        val secondPlayer = Player(
            name = secondPlayerName,
            playingPile = splitDeck.secondHalf.toMutableList()
        )

        return Game(firstPlayer, secondPlayer, suits)
    }
}