package com.jime.game.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.jime.game.domain.model.*
import com.jime.game.domain.model.getPoints
import com.jime.game.presentation.R
import com.jime.game.presentation.databinding.ActivityMainBinding
import com.jime.game.presentation.ui.extensions.getColorId
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.model.GameUiState.*
import com.jime.game.presentation.ui.viemodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewsListeners()
        observeUi()

        lifecycleScope.launch {
            updateGame(viewModel.uiState.first())
        }
    }

    private fun initViewsListeners() {
        binding.apply {
            startNewGameButton.setOnClickListener {
                viewModel.startNewGame()
            }
            player1Pile.setOnClickPileListener {
                viewModel.onPlayer1SelectCard()
            }
            player2Pile.setOnClickPileListener {
                viewModel.onPlayer2SelectedCard()
            }
        }
    }

    private fun observeUi() {
        lifecycleScope.launch {
            viewModel.uiState.collect { newUiState ->
                when (newUiState) {
                    is NewGame -> {
                        updateGame(newUiState)
                        showBoard()
                    }
                    is SelectingCards,
                    is StartNewRound -> {
                        updatePlayingBoard(
                            card1 = newUiState.player1SelectedCard,
                            card2 = newUiState.player2SelectedCard
                        )
                    }
                    is FinishedRound -> {
                        showRoundWinner(newUiState.winner)
                        updatePlayersPoints(newUiState.game.player1, newUiState.game.player2)
                    }
                    is FinishedGame -> {
                        showGameWinner(newUiState.winner)
                    }
                }
            }
        }
    }

    private fun updateGame(newState: GameUiState) {
        newState.apply {
            updateGameSuits(game.suitsWeight)
            updatePlayersPoints(game.player1, game.player2)
            updatePlayingBoard(player1SelectedCard, player2SelectedCard)
        }
    }

    private fun updatePlayersPoints(firstPlayer: Player1, secondPlayer: Player2) {
        binding.apply {
            player1Pile.updateCounter(firstPlayer.getPoints())
            player2Pile.updateCounter(secondPlayer.getPoints())
        }
    }

    private fun updatePlayingBoard(card1: Card?, card2: Card?) {
        binding.playingBoard.updateCards(card1, card2)
    }

    private fun updateGameSuits(suits: List<Suit>) {
        binding.suits.updateSuits(suits)
    }

    private fun showRoundWinner(player: Player) {
        val playerPile = when (player) {
            is Player1 -> binding.player1Pile
            else -> binding.player2Pile
        }
        playerPile.highLightPileAsWinner { viewModel.onRoundWinnerShown() }
    }

    private fun showBoard() {
        binding.apply {
            boardAnimator.displayedChild = boardAnimator.indexOfChild(playingBoard)
        }
    }

    private fun showGameWinner(player: Player?) {
        binding.apply {
            boardAnimator.displayedChild = boardAnimator.indexOfChild(gameWinner)

            val colorResource = ResourcesCompat.getColor(resources, player.getColorId(), null)
            gameWinner.setTextColor(colorResource)
            gameWinner.text = getWinnerText(player)
        }
    }

    private fun getWinnerText(player: Player?): String {
        player?.let {
            return "${getString(R.string.winner)} \n ${player.name}"
        }
        return getString(R.string.draw_game)
    }
}