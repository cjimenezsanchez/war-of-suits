package com.jime.game.presentation.ui.model

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

sealed class GameUiState(
    open val game: Game,
    open val player1SelectedCard: Card?,
    open val player2SelectedCard: Card?
) {
    data class NewGame(
        override val game: Game,
    ) : GameUiState(game, null, null)

    data class SelectingCards(
        override val game: Game,
        override val player1SelectedCard: Card?,
        override val player2SelectedCard: Card?,
    ) : GameUiState(game, player1SelectedCard, player2SelectedCard)

    data class FinishedRound(
        override val game: Game,
        override val player1SelectedCard: Card?,
        override val player2SelectedCard: Card?,
        val winner: Player
    ) : GameUiState(game, player1SelectedCard, player2SelectedCard)

    data class StartNewRound(
        override val game: Game
    ) : GameUiState(game, null, null)

    data class FinishedGame(
        override val game: Game,
        val winner: Player?
    ) : GameUiState(game, null, null)
}
