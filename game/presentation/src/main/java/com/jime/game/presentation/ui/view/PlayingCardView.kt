package com.jime.game.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import com.jime.game.domain.model.Card
import com.jime.game.domain.model.valueAsString
import com.jime.game.presentation.R
import com.jime.game.presentation.databinding.ViewPlayingCardBinding
import com.jime.game.presentation.ui.extensions.getSuitDrawable

class PlayingCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private val binding: ViewPlayingCardBinding

    init {
        binding = ViewPlayingCardBinding.inflate(LayoutInflater.from(context), this)

        val attr = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)
        val emptyCardIconPlaceHolderAttr =
            attr.getResourceId(
                R.styleable.PlayingCardView_emptyCardIconPlaceHolder,
                R.drawable.ic_magneto
            )

        val emptyCardIcon = AppCompatResources.getDrawable(context, emptyCardIconPlaceHolderAttr)
        binding.cardPlaceHolderIcon.setImageDrawable(emptyCardIcon)

        attr.recycle()
    }

    fun updateCard(card: Card?) {
        card?.let {
            updateCardValues(card)
            showCard()
            return
        }
        showPlaceHolder()
    }

    private fun updateCardValues(card: Card) {
        val cardSuitIcon = AppCompatResources.getDrawable(context, card.getSuitDrawable())

        binding.apply {
            cardValue.text = card.valueAsString()
            topLeftIcon.setImageDrawable(cardSuitIcon)
            bottomRightIcon.setImageDrawable(cardSuitIcon)
        }
    }

    private fun showCard() {
        binding.apply {
            cardAnimator.displayedChild = cardAnimator.indexOfChild(cardContainer)
        }
    }

    private fun showPlaceHolder() {
        binding.apply {
            cardAnimator.displayedChild = cardAnimator.indexOfChild(cardPlaceHolderIcon)
        }
    }
}