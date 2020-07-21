package com.example.guide

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * Create by ChenLei on 2020/7/8
 * Describe: 自定义镂空布局
 */
open class MaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val DST_OUT by lazy { PorterDuffXfermode(PorterDuff.Mode.DST_OUT) }

        // 默认蒙板颜色
        private val DEFAULT_MASK_COLOR = Color.parseColor("#c0000000")
    }

    // 遮罩颜色
    private var mMaskColor = DEFAULT_MASK_COLOR

    // 画笔
    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    // 当前View的矩阵
    private val mRectF by lazy { RectF(0f, 0f, width.toFloat(), height.toFloat()) }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MaskView)
        repeat(ta.indexCount) {
            when (val itemId = ta.getIndex(it)) {
                R.styleable.MaskView_maskColor -> {
                    mMaskColor = ta.getColor(itemId, DEFAULT_MASK_COLOR)
                }
            }
        }
        with(mPaint) {
            color = mMaskColor
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }
    }

    private var mFlag = true

    override fun dispatchDraw(canvas: Canvas?) {
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        canvas?.let {
            with(mPaint) {
                color = mMaskColor
                xfermode = null
                it.drawRect(mRectF, this)
                color = Color.BLACK
                xfermode = DST_OUT
                onMask(it, this)
            }
        }
        super.dispatchDraw(canvas)
    }

    /**
     * 自动添加图层然后恢复
     */
//    private fun autoLayer(canvas: Canvas?, action: (Canvas) -> Unit) {
//        canvas?.also {
//            //新建一个图层
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                it.saveLayer(mRectF, mPaint)
//            } else {
//                it.saveLayer(mRectF, mPaint, Canvas.ALL_SAVE_FLAG)
//            }
//            action(it)
//        }?.restore()
//    }

    /**
     * 自定义镂空
     */
    open fun onMask(canvas: Canvas, paint: Paint) {
    }

}