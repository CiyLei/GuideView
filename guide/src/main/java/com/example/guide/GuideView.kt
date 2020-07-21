package com.example.guide

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Create by ChenLei on 2020/7/8
 * Describe: 引导View
 */
open class GuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaskView(context, attrs, defStyleAttr), IGuideController {

    // 镂空的矩形信息
    open var maskRect: IMaskRect? = null
        set(value) {
            field = value
            // 刷新UI
            invalidate()
        }

    // 引导控制的实际类型
    internal var guideController: IGuideController? = null

    /**
     * 下一个引导
     */
    override fun nextGuide() {
        guideController?.nextGuide()
    }

    /**
     * 上一个引导
     */
    override fun preGuide() {
        guideController?.preGuide()
    }

    /**
     * 默认镂空控件
     */
    override fun onMask(canvas: Canvas, paint: Paint) {
        // 获取高亮位置信息
        maskRect?.let {
            canvas.drawRect(it.getRect(), paint)
        }
    }

}