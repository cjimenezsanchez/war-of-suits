package com.jime.game.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.jime.game.domain.model.Card
import com.jime.game.presentation.R

class PlayingBoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val player1Card: PlayingCardView
    private val player2Card: PlayingCardView

    private var onHighLightFinished: () -> Unit = {}
    @Volatile private var animationCounter = 0

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_playing_board, this, true)

        player1Card = view.findViewById(R.id.player1_card)
        player2Card = view.findViewById(R.id.player2_card)

        orientation = HORIZONTAL
    }

    fun setOnHighLightFinishedListener(onFinished: () -> Unit) {
        onHighLightFinished = onFinished
    }

    fun onPlayer1WinsRound() {
        animateCards(winningCard = player1Card, losingCard = player2Card)
    }

    fun onPlayer2WinsRound() {
        animateCards(winningCard = player2Card, losingCard = player1Card)
    }

    private fun animateCards(winningCard: PlayingCardView, losingCard: PlayingCardView) {
        winningCard.highLightCardAsWinner {
            onAnimationFinished()
        }
        losingCard.highLightCardAsLoser {
            onAnimationFinished()
        }
    }

    @Synchronized private fun onAnimationFinished() {
        animationCounter++
        if (animationsAreFinished()) {
            resetAnimationCounter()
            onHighLightFinished()
        }
    }

    private fun resetAnimationCounter() {
        animationCounter = 0
    }

    private fun animationsAreFinished(): Boolean {
        return animationCounter == 2
    }

    fun updateCards(card1: Card?, card2: Card?) {
        player1Card.updateCard(card1)
        player2Card.updateCard(card2)
    }

}