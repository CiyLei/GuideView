package com.example.guide

import android.graphics.RectF
import android.view.View

/**
 * Create by ChenLei on 2020/7/8
 * Describe: 获取
 */
interface IMaskRect {
    /**
     * 获取矩形
     */
    fun getRect(): RectF

    /**
     * 根据View获取位置
     */
    class TargetView(private val mView: View) : IMaskRect {

        override fun getRect(): RectF {
            val loc = IntArray(2)
            mView.getLocationOnScreen(loc)
            return RectF(
                loc[0].toFloat(),
                loc[1].toFloat(),
                (loc[0] + mView.width).toFloat(),
                (loc[1] + mView.height).toFloat()
            )
        }
    }

    /**
     * 直接提供位置
     */
    class Rect(private val mRect: RectF) : IMaskRect {
        override fun getRect(): RectF = mRect
    }
}