package com.example.guidedemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.TextView
import com.example.guide.IMaskRect

/**
 * Create by ChenLei on 2020/7/21
 * Describe:
 */
class StarfishGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TestControllerGuideView(context, attrs, defStyleAttr) {

    // 派大星路径
    private val mPDXPath: Path by lazy {
        Path().apply {
            rMoveTo(350f, 450f)

            lineTo(300f, 600f)
            lineTo(0f, 600f)
            lineTo(200f, 700f)
            lineTo(150f, 1000f)

            lineTo(400f, 800f)

            lineTo(650f, 1000f)
            lineTo(600f, 700f)
            lineTo(800f, 600f)
            lineTo(500f, 600f)

            lineTo(450f, 450f)

            quadTo(400f, 250f, 350f, 450f)
        }
    }

    // 派大星皮肤画笔
    private val mPDXSkinPaint: Paint by lazy {
        Paint().apply {
            strokeWidth = 10f
            style = Paint.Style.STROKE
            color = Color.parseColor("#F5AEAA")
        }
    }

    // 黑色线条画笔
    private val mBlackLinePaint: Paint by lazy {
        Paint().apply {
            strokeWidth = 10f
            style = Paint.Style.STROKE
            color = Color.BLACK
        }
    }

    companion object {
        fun newInstance(context: Context, maskRect: IMaskRect): StarfishGuideView {
            return StarfishGuideView(context).apply {
                this.maskRect = maskRect
            }
        }
    }

    override fun onMask(canvas: Canvas, paint: Paint) {
        maskRect?.getRect()?.let {
            canvas.drawPath(mPDXPath, paint)
        }
        canvas.drawPath(mPDXPath, mPDXSkinPaint)
        canvas.drawPoint(380f, 500f, mBlackLinePaint)
        canvas.drawPoint(410f, 500f, mBlackLinePaint)
        canvas.drawLine(350f, 550f, 440f, 550f, mBlackLinePaint)
    }

    override fun onShowDes(tv: TextView) {
        tv.text = "自定义镂空-派大星View"
    }
}