package com.jime.game.presentation.ui.model

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

sealed class GameUiState {

    data class NewGame(val game: Game) : GameUiState()
    data class SelectingCards(
        val player1SelectedCard: Card?,
        val player2SelectedCard: Card?
    ) : GameUiState()
    data class ShowRoundWinner(val winner: Player): GameUiState()
    data class FinishedRound(val game: Game) : GameUiState()
    data class FinishedGame(val winner: Player?): GameUiState()
}
