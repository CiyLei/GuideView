package com.example.guidedemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.example.guide.IMaskRect
import kotlin.math.sqrt

/**
 * Create by ChenLei on 2020/7/8
 * Describe:
 */
class CircleGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TestControllerGuideView(context, attrs, defStyleAttr) {

    companion object {
        fun newInstance(context: Context, maskRect: IMaskRect): CircleGuideView {
            return CircleGuideView(context).apply {
                this.maskRect = maskRect
            }
        }
    }

    override fun onMask(canvas: Canvas, paint: Paint) {
        maskRect?.getRect()?.let {
            val width = it.width() / 2
            val height = it.height() / 2
            canvas.drawCircle(
                it.centerX(),
                it.centerY(),
                sqrt(width * width + height * height),
                paint
            )
        }
    }

    override fun onShowDes(tv: TextView) {
        tv.text = "自定义镂空&动态修改View"
    }
}