package com.example.guidedemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import com.example.guide.GuideView
import com.example.guide.IMaskRect

/**
 * Create by ChenLei on 2020/7/8
 * Describe:
 */
open class ArrowGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GuideView(context, attrs, defStyleAttr) {

    companion object {
        fun newInstance(context: Context, maskRect: IMaskRect): ArrowGuideView {
            return ArrowGuideView(context).apply {
                this.maskRect = maskRect
            }
        }
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.guide_arrow, null, false)
        view.setOnClickListener { nextGuide() }
        addView(view)
    }

    override fun onMask(canvas: Canvas, paint: Paint) {
        maskRect?.getRect()?.let {
            val r = RectF(it)
            r.inset(-50f, -50f)
            canvas.drawRoundRect(r, 10f, 10f, paint)
        }
    }
}