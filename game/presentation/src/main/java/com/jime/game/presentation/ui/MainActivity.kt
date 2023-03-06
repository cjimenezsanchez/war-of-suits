package com.jime.game.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.jime.game.domain.model.*
import com.jime.game.domain.use_case.getPoints
import com.jime.game.presentation.R
import com.jime.game.presentation.databinding.ActivityMainBinding
import com.jime.game.presentation.ui.extensions.getColorId
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.model.GameUiState.NewGame
import com.jime.game.presentation.ui.viemodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    }

    private fun initViewsListeners() {
        binding.apply {
            startNewGameButton.setOnClickListener {
                viewModel.startNewGame()
            }
            player1Pile.setOnClickPileListener {
                viewModel.onFirstPlayerSelectCard()
            }
            player2Pile.setOnClickPileListener {
                viewModel.secondPlayerSelectedCard()
            }
            playingBoard.setOnHighLightFinishedListener {
                viewModel.onRoundWinnerShown()
            }
        }
    }

    private fun observeUi() {
        lifecycleScope.launch {
            viewModel.uiState.collect { newUiState ->
                println("Collect new state: $newUiState")
                when (newUiState) {
                    is NewGame -> {
                        updateGameSuits(newUiState.game.suitsWeight)
                        updatePlayingBoard(null, null)
                        updateCurrentPoints(newUiState.game)
                        showBoard()
                    }
                    is GameUiState.SelectingCards -> {
                        updatePlayingBoard(
                            card1 = newUiState.player1SelectedCard,
                            card2 = newUiState.player2SelectedCard
                        )
                    }
                    is GameUiState.FinishedRound -> {
                        showRoundWinner(newUiState.winner)
                        updateCurrentPoints(newUiState.game)
                    }
                    is GameUiState.StartNewRound -> {
                        updateGameSuits(newUiState.game.suitsWeight)
                        updatePlayingBoard(null, null)
                    }
                    is GameUiState.FinishedGame -> {
                        showGameWinner(newUiState.winner)
                    }
                }
            }
        }
    }

    private fun updateCurrentPoints(game: Game) {
        binding.apply {
            player1Pile.updateCounter(game.player1.getPoints())
            player2Pile.updateCounter(game.player2.getPoints())
        }
    }

    private fun updatePlayingBoard(card1: Card?, card2: Card?) {
        binding.playingBoard.updateCards(card1, card2)
    }

    private fun updateGameSuits(suits: List<Suit>) {
        binding.suits.updateSuits(suits)
    }

    private fun showRoundWinner(player: Player) {
        when (player) {
            is Player1 -> {
                //binding.playingBoard.onPlayer1WinsRound()
                binding.player1Pile.highLightCardAsWinner {
                    viewModel.onRoundWinnerShown()
                }
            }
            is Player2 -> {
                //binding.playingBoard.onPlayer2WinsRound()
                binding.player2Pile.highLightCardAsWinner {
                    viewModel.onRoundWinnerShown()
                }
            }
        }
    }

    private fun showBoard() {
        binding.apply {
           boardAnimator.displayedChild = boardAnimator.indexOfChild(playingBoard)
        }
    }

    private fun showGameWinner(player: Player?) {
        binding.apply {
            boardAnimator.displayedChild = boardAnimator.indexOfChild(gameWinner)

            val promptText = if (player == null) {
                getString(R.string.draw_game)
            } else {
                "Winner \n ${player.name}"
            }

            gameWinner.text = promptText
            val colorResource = ResourcesCompat.getColor(resources, player.getColorId(), null)
            gameWinner.setTextColor(colorResource)
        }
    }
}