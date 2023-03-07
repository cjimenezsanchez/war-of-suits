package com.jime.game.domain

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Game
import com.jime.game.domain.model.Player

interface GameHelper {

    fun startGame(playerName1: String, playerName2: String): Game

    fun getPlayerNextCard(player: Player): Card?

    fun playNextRound(game: Game)

    fun getLastRoundWinner(game: Game): Player?

    fun isGameFinished(game: Game): Boolean

    fun getGameWinner(game: Game): Player?

}