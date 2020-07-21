package com.example.guidedemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.guide.GuideView
import com.example.guide.IMaskRect

/**
 * Create by ChenLei on 2020/7/8
 * Describe:
 */
open class TestControllerGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GuideView(context, attrs, defStyleAttr) {

    companion object {
        fun newInstance(context: Context, maskRect: IMaskRect): TestControllerGuideView {
            return TestControllerGuideView(context).apply {
                this.maskRect = maskRect
            }
        }
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.guide_test, null, false)
        view.findViewById<Button>(R.id.btnP).setOnClickListener {
            preGuide()
        }
        view.findViewById<Button>(R.id.btnN).setOnClickListener {
            nextGuide()
        }
        addView(view)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onShowDes(findViewById(R.id.tv))
    }

    open fun onShowDes(tv: TextView) {
        if (maskRect is IMaskRect.TargetView) {
            tv.text = "根据View镂空"
        } else {
            tv.text = "绝对坐标镂空"
        }
    }

}