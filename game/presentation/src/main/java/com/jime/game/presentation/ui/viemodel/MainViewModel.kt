package com.jime.game.presentation.ui.viemodel

import androidx.lifecycle.ViewModel
import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.use_case.GetWinnerUseCase
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
    private val getWinner: GetWinnerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState>(FinishedGame(null))
    val uiState: StateFlow<GameUiState> get() = _uiState

    private lateinit var game: Game
    private var player1SelectedCard: Card? = null
    private var player2SelectedCard: Card? = null

    init {
        startNewGame()
    }

    fun startNewGame() {
        println("Start new game called")
        game = startGame("Magneto", "Professor X")
        clearSelectedCards()
        updateUiState(newState = NewGame(game))
    }

    fun onFirstPlayerSelectCard() {
        if (player1SelectedCard == null) {
            player1SelectedCard = getPlayerNextCard(game.player1)
            onSelectedCard()
        }
    }

    fun secondPlayerSelectedCard() {
        if (player2SelectedCard == null) {
            player2SelectedCard = getPlayerNextCard(game.player2)
            onSelectedCard()
        }
    }

    fun onRoundWinnerShown() {
        clearSelectedCards()
        updateUiState(newState = FinishedRound(game))
        if (isGameFinished(game)) {
            updateUiState(newState = FinishedGame(getWinner(game)))
        }
    }

    private fun onSelectedCard() {
        updateUiState(newState = SelectingCards(player1SelectedCard, player2SelectedCard))
        playRoundIfReady()
    }

    private fun bothCardsSelected(): Boolean {
        return player1SelectedCard != null && player2SelectedCard != null
    }

    private fun playRoundIfReady() {
        if (bothCardsSelected()) {
            playNextRound(game)
            updateUiState(newState = ShowRoundWinner(game.player1))
        }
    }

    private fun clearSelectedCards() {
        player1SelectedCard = null
        player2SelectedCard = null
    }

    private fun updateUiState(newState: GameUiState) {
        _uiState.value = newState
    }
}