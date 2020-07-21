package com.example.guide

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Create by ChenLei on 2020/7/8
 * Describe: 修正占位View的引导
 */
open class HolderGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GuideView(context, attrs, defStyleAttr) {

    open var holderLayout: ConstraintLayout? = null
        set(value) {
            field = value?.apply {
                // 获取站位的View
                this@HolderGuideView.mHolderView = this.findViewWithTag(TAG_HOLDER)
                this.visibility = View.INVISIBLE
                mHolderView.viewTreeObserver.addOnGlobalLayoutListener(mCorrectLocationListener)
                this@HolderGuideView.addView(this)
            }
        }

    // 占位的View
    private lateinit var mHolderView: View

    companion object {
        private const val TAG_HOLDER = "holder"

        fun newInstance(
            context: Context,
            maskRect: IMaskRect,
            holderLayout: ConstraintLayout
        ): HolderGuideView {
            return HolderGuideView(context).apply {
                this.maskRect = maskRect
                this.holderLayout = holderLayout
            }
        }
    }

    /**
     * 修改位置的回调
     */
    private val mCorrectLocationListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            mHolderView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            // 修正完位置大小后再显示出来
            mHolderView.viewTreeObserver.addOnGlobalLayoutListener(mVisibleListener)
            // 将站位的View的位置大小设置为镂空的View
            maskRect?.getRect()?.let {
                val layoutParams =
                    ConstraintLayout.LayoutParams(mHolderView.layoutParams)
                layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.width = it.width().toInt()
                layoutParams.height = it.height().toInt()
                layoutParams.topMargin = it.top.toInt()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.marginStart = it.left.toInt()
                } else {
                    layoutParams.leftMargin = it.left.toInt()
                }
                mHolderView.layoutParams = layoutParams
            }
        }
    }

    /**
     * 显示布局的回调
     */
    private val mVisibleListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            mHolderView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            holderLayout?.visibility = View.VISIBLE
        }
    }

}