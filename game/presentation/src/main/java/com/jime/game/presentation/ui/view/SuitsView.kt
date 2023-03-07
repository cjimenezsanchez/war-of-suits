package com.jime.game.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.jime.game.domain.model.Suit
import com.jime.game.presentation.R
import com.jime.game.presentation.databinding.ViewSuitsBinding
import com.jime.game.presentation.ui.extensions.getSuitDrawable

class SuitsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: ViewSuitsBinding
    private val suitViews: List<ImageView>

    init {
        binding = ViewSuitsBinding.inflate(LayoutInflater.from(context), this)
        binding.apply {
            suitViews = listOf(firstSuit, secondSuit, thirdSuit, fourthSuit)
        }

        orientation = HORIZONTAL
    }

    fun updateSuits(suits: List<Suit>) {
        require(suits.size == 4)
        suitViews.zip(suits)
            .forEach { (view, suit) ->
                val drawable = AppCompatResources.getDrawable(
                    context,
                    suit.getSuitDrawable()
                )
                view.setImageDrawable(drawable)
            }
    }
}