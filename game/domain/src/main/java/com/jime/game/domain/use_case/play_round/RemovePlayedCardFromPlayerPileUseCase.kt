package com.jime.game.domain.use_case.play_round

import com.jime.game.domain.model.Card
import com.jime.game.domain.model.Player
import com.jime.game.domain.model.removedPlayedCard

class RemovePlayedCardFromPlayerPileUseCase {

    operator fun invoke(player: Player, card: Card) {
        if (!player.playingPile.contains(card)) {
            throw NoSuchElementException()
        }
        player.removedPlayedCard(card)
    }

}