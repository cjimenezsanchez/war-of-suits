package com.jime.game.presentation.ui.viemodel

import androidx.lifecycle.ViewModel
import com.jime.game.domain.model.Card
import com.jime.game.domain.use_case.GetLastRoundWinnerUseCase
import com.jime.game.domain.use_case.GetGameWinnerUseCase
import com.jime.game.domain.use_case.IsGameFinishedUseCase
import com.jime.game.domain.use_case.play_round.GetPlayerNextCardUseCase
import com.jime.game.domain.use_case.play_round.PlayNextRoundUseCase
import com.jime.game.domain.use_case.start_game.StartNewGameUseCase
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.model.GameUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val startGame: StartNewGameUseCase,
    private val getPlayerNextCard: GetPlayerNextCardUseCase,
    private val playNextRound: PlayNextRoundUseCase,
    private val isGameFinished: IsGameFinishedUseCase,
    private val getLastRoundWinner: GetLastRoundWinnerUseCase,
    private val getGameWinner: GetGameWinnerUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState>(NewGame(startGame("", "")))
    val uiState: StateFlow<GameUiState> get() = _uiState

    init {
        startNewGame()
    }

    fun startNewGame() {
        updateUiState(newState = NewGame(startGame("Magneto", "Professor X")))
    }

    fun onPlayer1SelectCard() {
        if (getPlayer1CurrentCard() == null) {
            onCardsSelected(
                card1 = getPlayerNextCard(_uiState.value.game.player1),
                card2 = getPlayer2CurrentCard()
            )

        }
    }

    fun onPlayer2SelectedCard() {
        if (getPlayer2CurrentCard() == null) {
            onCardsSelected(
                card1 = getPlayer1CurrentCard(),
                card2 = getPlayerNextCard(_uiState.value.game.player2)
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

            playNextRound(game)
            getLastRoundWinner(game)?.let { winner ->
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
        if (isGameFinished(game)) {
            val winner = getGameWinner(game)
            updateUiState(newState = FinishedGame(game, winner))
        }
    }

    private fun updateUiState(newState: GameUiState) {
        _uiState.value = newState
    }
}