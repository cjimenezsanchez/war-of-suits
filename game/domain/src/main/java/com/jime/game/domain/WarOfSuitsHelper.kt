package com.jime.game.domain

import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player
import com.jime.game.domain.use_case.GetGameWinnerUseCase
import com.jime.game.domain.use_case.GetLastRoundWinnerUseCase
import com.jime.game.domain.use_case.IsGameFinishedUseCase
import com.jime.game.domain.use_case.play_round.GetPlayerNextCardUseCase
import com.jime.game.domain.use_case.play_round.PlayNextRoundUseCase
import com.jime.game.domain.use_case.start_game.StartNewGameUseCase
import javax.inject.Inject

class WarOfSuitsHelper @Inject constructor(
    private val startGameUseCase: StartNewGameUseCase,
    private val getPlayerNextCardUseCase: GetPlayerNextCardUseCase,
    private val playNextRoundUseCase: PlayNextRoundUseCase,
    private val isGameFinishedUseCase: IsGameFinishedUseCase,
    private val getLastRoundWinnerUseCase: GetLastRoundWinnerUseCase,
    private val getGameWinnerUseCase: GetGameWinnerUseCase
): GameHelper {

    override fun startGame(playerName1: String, playerName2: String): Game {
        return startGameUseCase(playerName1, playerName2)
    }

    override fun getPlayerNextCard(player: Player) = getPlayerNextCardUseCase(player)

    override fun playNextRound(game: Game) = playNextRoundUseCase(game)

    override fun getLastRoundWinner(game: Game) = getLastRoundWinnerUseCase(game)

    override fun isGameFinished(game: Game) = isGameFinishedUseCase(game)

    override fun getGameWinner(game: Game) = getGameWinnerUseCase(game)
}