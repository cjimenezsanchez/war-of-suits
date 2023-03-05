package com.jime.game.presentation.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.jime.game.domain.model.Card
import com.jime.game.domain.use_case.valueAsString
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
    private val emptyCardPlaceHolder: String
    private val cardContainer: RelativeLayout

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)

        emptyCardPlaceHolder =
            attr.getString(R.styleable.PlayingCardView_emptyCardPlaceHolder) ?: ""

        val view = LayoutInflater.from(context).inflate(R.layout.view_playing_card, this, true)
        topLeftIcon = view.findViewById(R.id.top_left_icon)
        bottomRightIcon = view.findViewById(R.id.bottom_right_icon)
        cardValueText = view.findViewById(R.id.card_value)
        cardContainer = view.findViewById(R.id.card_container)

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

        val cardValue = getCardTextValue(card)
        val cardSuitIcon = getCardSuitIcon(card)

        cardValueText.text = cardValue
        cardValueText.alpha = getCardTextAlpha(card)
        topLeftIcon.setImageDrawable(cardSuitIcon)
        bottomRightIcon.setImageDrawable(cardSuitIcon)
    }

    private fun getCardTextValue(card: Card?): String {
        card?.let {
            return card.valueAsString()
        }
        return emptyCardPlaceHolder
    }

    private fun getCardTextAlpha(card: Card?): Float {
        card?.let {
            return 1.0f
        }
        return 0.2f
    }

    private fun getCardSuitIcon(card: Card?): Drawable? {
        card?.let {
            return AppCompatResources.getDrawable(context, card.getSuitDrawable())
        }
        return null
    }
}