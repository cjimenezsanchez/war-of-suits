package com.jime.game.presentation.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import com.jime.game.presentation.R

class PlayerPileView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    companion object {
        private const val COUNTER_START = 1
    }

    private val counterText: TextView

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.PlayerPileView)
        val counterAttr = attr.getInt(R.styleable.PlayerPileView_counterPosition, 0)
        val playerColorAttr =
            attr.getResourceId(R.styleable.PlayerPileView_playerColor, R.color.white)
        val playerColor = resources.getColor(playerColorAttr, null)
        val playerIconAttr =
            attr.getResourceId(R.styleable.PlayerPileView_playerIcon, R.drawable.ic_magneto)

        val view = if (counterAttr == COUNTER_START) {
            LayoutInflater.from(context).inflate(R.layout.view_player_pile_counter_left, this, true)
        } else {
            LayoutInflater.from(context)
                .inflate(R.layout.view_player_pile_counter_right, this, true)
        }

        counterText = view.findViewById(R.id.counter)
        counterText.setTextColor(playerColor)
        counterText.text = "0"

        val pileReverse = view.findViewById<ImageView>(R.id.player_pile_reverse)
        pileReverse.setBackgroundColor(playerColor)

        val playerIcon = view.findViewById<ImageView>(R.id.player_pile_icon)
        playerIcon.setImageDrawable(AppCompatResources.getDrawable(context, playerIconAttr))
        attr.recycle()

        orientation = HORIZONTAL
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
            duration = 500
        )
    }
}

fun TextView.animateInt(initialValue: Int, finalValue: Int, duration: Long) {
    val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
    valueAnimator.duration = duration
    valueAnimator.addUpdateListener { animator -> this.text = animator.animatedValue.toString() }
    valueAnimator.start()
}