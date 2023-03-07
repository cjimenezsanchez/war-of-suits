package com.jime.game.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.jime.game.domain.model.Card
import com.jime.game.domain.model.valueAsString
import com.jime.game.presentation.R
import com.jime.game.presentation.ui.extensions.getSuitDrawable

class PlayingCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    companion object {
        private const val ANIMATION_INTERVAL = 1000L
        private const val SIZE_DELTA = 0.2f
    }

    private val topLeftIcon: AppCompatImageView
    private val bottomRightIcon: AppCompatImageView
    private val cardValueText: TextView
    private val cardContainer: RelativeLayout
    private val iconPlaceHolder: AppCompatImageView
    private val viewAnimator: ViewAnimator

    init {

        val view = LayoutInflater.from(context).inflate(R.layout.view_playing_card, this, true)
        topLeftIcon = view.findViewById(R.id.top_left_icon)
        bottomRightIcon = view.findViewById(R.id.bottom_right_icon)
        cardValueText = view.findViewById(R.id.card_value)
        cardContainer = view.findViewById(R.id.card_container)
        iconPlaceHolder = view.findViewById(R.id.card_place_holder_icon)
        viewAnimator = view.findViewById(R.id.card_animator)

        val attr = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)

        val emptyCardIconPlaceHolderAttr =
            attr.getResourceId(
                R.styleable.PlayingCardView_emptyCardIconPlaceHolder,
                R.drawable.ic_magneto
            )

        val emptyCardIcon = AppCompatResources.getDrawable(context, emptyCardIconPlaceHolderAttr)
        iconPlaceHolder.setImageDrawable(emptyCardIcon)

        attr.recycle()
    }

    fun highLightCardAsLoser(then: () -> Unit) {
        decreaseCard {
            increaseCard {
                then()
            }
        }
    }

    fun highLightCardAsWinner(then: () -> Unit) {
        increaseCard {
            decreaseCard {
                then()
            }
        }
    }

    private fun increaseCard(then: () -> Unit = {}) {
        animateSizeBy(SIZE_DELTA, then)
    }

    private fun decreaseCard(then: () -> Unit) {
        animateSizeBy(-SIZE_DELTA, then)
    }

    private fun animateSizeBy(delta: Float, then: () -> Unit) {
        this.animate()
            .scaleXBy(delta)
            .scaleYBy(delta)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction(then)
            .duration = ANIMATION_INTERVAL
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

        cardValueText.text = card.valueAsString()
        topLeftIcon.setImageDrawable(cardSuitIcon)
        bottomRightIcon.setImageDrawable(cardSuitIcon)
    }

    private fun showCard() {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(cardContainer)
    }

    private fun showPlaceHolder() {
        viewAnimator.showNext()
       viewAnimator.displayedChild = viewAnimator.indexOfChild(iconPlaceHolder)
    }

//    private fun getCardTextValue(card: Card?): String {
//        card?.let {
//            return card.valueAsString()
//        }
//        return emptyCardPlaceHolder
//    }
//
//    private fun getCardTextAlpha(card: Card?): Float {
//        card?.let {
//            return 1.0f
//        }
//        return 0.2f
//    }
//
//    private fun getCardSuitIcon(card: Card?): Drawable? {
//        card?.let {
//            return AppCompatResources.getDrawable(context, card.getSuitDrawable())
//        }
//        return null
//    }
}