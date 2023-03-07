package com.jime.game.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.jime.game.domain.model.Card
import com.jime.game.presentation.databinding.ViewPlayingBoardBinding

class PlayingBoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: ViewPlayingBoardBinding

    init {
        binding = ViewPlayingBoardBinding.inflate(LayoutInflater.from(context), this)
        orientation = HORIZONTAL
    }

    fun updateCards(card1: Card?, card2: Card?) {
        binding.apply {
            player1Card.updateCard(card1)
            player2Card.updateCard(card2)
        }
    }
}