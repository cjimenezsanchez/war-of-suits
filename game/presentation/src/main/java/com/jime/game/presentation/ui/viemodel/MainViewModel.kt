package com.jime.game.presentation.ui.viemodel

import androidx.lifecycle.ViewModel
import com.jime.game.domain.GameHelper
import com.jime.game.domain.model.Card
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.model.GameUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val gameHelper: GameHelper) : ViewModel() {

    private val defaultSateGame: GameUiState = NewGame(gameHelper.startGame("", ""))
    private val _uiState = MutableStateFlow(defaultSateGame)
    val uiState: StateFlow<GameUiState> get() = _uiState

    init {
        startNewGame()
    }

    fun startNewGame() {
        updateUiState(newState = NewGame(gameHelper.startGame("Magneto", "Professor X")))
    }

    fun onPlayer1SelectCard() {
        if (getPlayer1CurrentCard() == null) {
            onCardsSelected(
                card1 = gameHelper.getPlayerNextCard(_uiState.value.game.player1),
                card2 = getPlayer2CurrentCard()
            )
        }
    }

    fun onPlayer2SelectedCard() {
        if (getPlayer2CurrentCard() == null) {
            onCardsSelected(
                card1 = getPlayer1CurrentCard(),
                card2 = gameHelper.getPlayerNextCard(_uiState.value.game.player2)
            )
        }
    }

    private fun getPlayer1CurrentCard() = _uiState.value.player1SelectedCard
    private fun getPlayer2CurrentCard() = _uiState.value.player2SelectedCard

    private fun onCardsSelected(card1: Card?, card2: Card?) {
        updateUiState(
            newState = SelectingCards(
                game = _uiState.value.game,
                player1SelectedCard = card1,
                player2SelectedCard = card2
            )
        )
        playRoundIfReady()
    }

    private fun playRoundIfReady() {
        if (bothCardsSelected()) {
            val game = _uiState.value.game

            gameHelper.playNextRound(game)
            gameHelper.getLastRoundWinner(game)?.let { winner ->
                updateUiState(
                    newState = FinishedRound(
                        game = game,
                        player1SelectedCard = getPlayer1CurrentCard(),
                        player2SelectedCard = getPlayer2CurrentCard(),
                        winner = winner
                    )
                )
            }
        }
    }

    private fun bothCardsSelected() =
        getPlayer1CurrentCard() != null && getPlayer2CurrentCard() != null

    fun onRoundWinnerShown() {
        val game = _uiState.value.game
        updateUiState(newState = StartNewRound(game))
        if (gameHelper.isGameFinished(game)) {
            val winner = gameHelper.getGameWinner(game)
            updateUiState(newState = FinishedGame(game, winner))
        }
    }

    private fun updateUiState(newState: GameUiState) {
        _uiState.value = newState
    }
}