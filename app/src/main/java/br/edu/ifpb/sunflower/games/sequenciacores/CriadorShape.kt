package br.edu.ifpb.sunflower.games.sequenciacores

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ViewUtils

class CriadorShape {

    @SuppressLint("RestrictedApi")
    fun criarShapeRedondo(context: Context, corComponente: Int, bg: GradientDrawable): Drawable {

        val corPreenchimento = ContextCompat.getColor(context, corComponente)
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = bg.shape
        gradientDrawable.setColor(corPreenchimento)
        gradientDrawable.setStroke(ViewUtils.dpToPx(context, 3).toInt(), Color.BLACK)
        gradientDrawable.alpha = bg.alpha
        gradientDrawable.cornerRadius = bg.cornerRadius

        return gradientDrawable
    }

    fun duplicarTextView(tv: View, contexto: Context): TextView {
        val novoCampoCor = TextView(contexto)
        novoCampoCor.layoutParams = tv.layoutParams
        novoCampoCor.background = tv.background
        return novoCampoCor
    }


}