package com.jime.game.presentation

import com.jime.game.domain.model.*
import com.jime.game.domain.use_case.play_round.*
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.viemodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Test
    fun `when starting new game verify new uiState is NewGame`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())
        viewModel.startNewGame()

        val result = viewModel.uiState.first()

        assertTrue(result is GameUiState.NewGame)
        assertEquals(result.player1SelectedCard, null)
        assertEquals(result.player2SelectedCard, null)
    }

    @Test
    fun `when selecting card 1 verify new uiState is SelectingCards`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())
        viewModel.onPlayer1SelectCard()

        val result = viewModel.uiState.first()
        val expectedCard = Card(Suit(Suit.Type.CLUBS, 0), 4)

        assertTrue(result is GameUiState.SelectingCards)
        assertEquals(expectedCard, result.player1SelectedCard)
    }

    @Test
    fun `when selecting card 2 verify new uiState is SelectingCards`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())
        viewModel.onPlayer2SelectedCard()

        val result = viewModel.uiState.first()
        val expectedCard = Card(Suit(Suit.Type.CLUBS, 0), 8)

        assertTrue(result is GameUiState.SelectingCards)
        assertEquals(expectedCard, result.player2SelectedCard)
    }

    @Test
    fun `when selecting both card verify new uiState is FinishedRound`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())
        viewModel.onPlayer1SelectCard()
        viewModel.onPlayer2SelectedCard()

        val result = viewModel.uiState.first()

        val expectedCard1 = Card(Suit(Suit.Type.CLUBS, 0), 4)
        val expectedCard2 = Card(Suit(Suit.Type.CLUBS, 0), 8)

        assertTrue(result is GameUiState.FinishedRound)
        assertEquals(expectedCard1, result.player1SelectedCard)
        assertEquals(expectedCard2, result.player2SelectedCard)
        assertTrue((result as GameUiState.FinishedRound).winner is Player2)
    }

    @Test
    fun `when on round finished verify new uiState is StartNewRound`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())
        viewModel.onRoundWinnerShown()

        val result = viewModel.uiState.first()

        assertTrue(result is GameUiState.StartNewRound)
        assertEquals(null, result.player1SelectedCard)
        assertEquals(null, result.player2SelectedCard)
    }

    @Test
    fun `when all rounds played verify new uiState is FinishedGame`(): Unit = runTest {
        viewModel = MainViewModel(FakeGameHelper())

        repeat(2) {
            playRound()
        }

        val result = viewModel.uiState.first()
        assertTrue(result is GameUiState.FinishedGame)
        assertTrue((result as GameUiState.FinishedGame).winner is Player2)
    }

    private fun playRound() {
        viewModel.onPlayer1SelectCard()
        viewModel.onPlayer2SelectedCard()
        viewModel.onRoundWinnerShown()
    }
}