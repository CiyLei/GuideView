package com.example.guidedemo

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.guide.IMaskRect

/**
 * Create by ChenLei on 2020/7/8
 * Describe:
 */
class AnimGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TestControllerGuideView(context, attrs, defStyleAttr) {

    var anim: ValueAnimator? = null

    companion object {
        fun newInstance(context: Context, maskRect: IMaskRect): AnimGuideView {
            return AnimGuideView(context).apply {
                this.maskRect = maskRect
            }
        }
    }

    init {
        findViewById<Button>(R.id.btnR).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                anim?.start()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (anim == null) {
            anim = ValueAnimator.ofFloat(0.0f, 1.0f).apply {
                duration = 1500
                addUpdateListener {
                    invalidate()
                }
            }
        }
        anim?.start()
    }

    override fun onMask(canvas: Canvas, paint: Paint) {
        val value = anim?.animatedValue as Float
        maskRect?.getRect()?.let {
            val rect = RectF(it.left, it.top, it.left + it.width() * value, it.bottom)
            canvas.drawRect(rect, paint)
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val value = anim?.animatedValue as Float
        maskRect?.getRect()?.let {
            val rect = RectF(it.left, it.top, it.left + it.width() * value, it.bottom)
            canvas?.drawRect(rect, Paint().apply {
                color = ContextCompat.getColor(context, R.color.teal_200)
                style = Paint.Style.STROKE
                strokeWidth = 6.0f
                isAntiAlias = true
                pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 10f)
            })
        }
    }

    override fun onShowDes(tv: TextView) {
        tv.text = "动画镂空&描边"
    }
}