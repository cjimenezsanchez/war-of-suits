package com.jime.game.presentation.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import com.jime.game.presentation.R

class PlayerPileView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    companion object {
        private const val AT_LEFT_POSITION = 1
        private const val SIZE_DELTA = 0.2f
        private const val ANIMATION_INTERVAL = 1000L


    }

    private val counterText: TextView
    private val pileReverse: View
    private val pileBottom: View

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.PlayerPileView)
        val counterAttr = attr.getInt(R.styleable.PlayerPileView_counterPosition, 0)
        val playerColorAttr =
            attr.getResourceId(R.styleable.PlayerPileView_playerColor, R.color.white)
        val playerColor = resources.getColor(playerColorAttr, null)
        val playerIconAttr =
            attr.getResourceId(R.styleable.PlayerPileView_playerIcon, R.drawable.ic_magneto)

        val view = if (counterAttr == AT_LEFT_POSITION) {
            LayoutInflater.from(context).inflate(R.layout.view_player_pile_counter_left, this, true)
        } else {
            LayoutInflater.from(context)
                .inflate(R.layout.view_player_pile_counter_right, this, true)
        }

        counterText = view.findViewById(R.id.counter)
        counterText.setTextColor(playerColor)
        counterText.text = "0"

        pileReverse = view.findViewById(R.id.player_pile_reverse)
        pileReverse.setBackgroundColor(playerColor)
        pileBottom = view.findViewById(R.id.pile_bottom)
        pileBottom.setBackgroundColor(playerColor)

        val playerIcon = view.findViewById<ImageView>(R.id.player_pile_icon)
        playerIcon.setImageDrawable(AppCompatResources.getDrawable(context, playerIconAttr))
        attr.recycle()

        orientation = HORIZONTAL
    }

    fun setOnClickPileListener(onClick: () -> Unit) {
        pileReverse.setOnClickListener { onClick() }
    }

    fun updateCounter(points: Int) {
        val initialValue = try {
            counterText.text.toString().toInt()
        } catch (e: Exception) {
            0
        }
        counterText.animateInt(
            initialValue = initialValue,
            finalValue = points,
            duration = ANIMATION_INTERVAL
        )
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

}

fun TextView.animateInt(initialValue: Int, finalValue: Int, duration: Long) {
    val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
    valueAnimator.duration = duration
    valueAnimator.addUpdateListener { animator -> this.text = animator.animatedValue.toString() }
    valueAnimator.start()
}