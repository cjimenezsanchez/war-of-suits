package com.jime.game.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.Suit
import com.jime.game.domain.use_case.getPoints
import com.jime.game.presentation.databinding.ActivityMainBinding
import com.jime.game.presentation.ui.model.GameUiState
import com.jime.game.presentation.ui.model.GameUiState.NewGame
import com.jime.game.presentation.ui.viemodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListeners()
        observeUi()
    }

    private fun initClickListeners() {
        binding.apply {
            startNewGameButton.setOnClickListener {
                viewModel.startNewGame()
            }
            player1Pile.setOnClickListener {
                viewModel.onFirstPlayerSelectCard()
            }
            player2Pile.setOnClickListener {
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
                        updateSuitsWeights(newUiState.game.suitsWeight)
                        updateCentralPiles(null, null)
                        updateCurrentPoints(newUiState.game)
                    }
                    is GameUiState.SelectingCards -> {
                        updateCentralPiles(
                            card1 = newUiState.player1SelectedCard,
                            card2 = newUiState.player2SelectedCard
                        )
                    }
                    is GameUiState.ShowRoundWinner -> {
                        showRoundWinner(newUiState.winner)
                    }
                    is GameUiState.FinishedRound -> {
                        updateSuitsWeights(newUiState.game.suitsWeight)
                        updateCurrentPoints(newUiState.game)
                        updateCentralPiles(null, null)
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

    private fun updateCentralPiles(card1: Card?, card2: Card?) {
        binding.playingBoard.updateCards(card1, card2)
    }

    private fun updateSuitsWeights(suits: List<Suit>) {
        binding.suits.updateSuits(suits)
    }

    private fun showRoundWinner(player: Player) {
        if (Random.nextBoolean()) {
            binding.playingBoard.onPlayer1WinsRound()
        } else {
            binding.playingBoard.onPlayer2WinsRound()
        }
    }

    private fun showGameWinner(player: Player?) {

    }
}