package com.example.guidedemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.example.guide.IMaskRect

/**
 * Create by ChenLei on 2020/7/8
 * Describe:
 */
class MutableGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TestControllerGuideView(context, attrs, defStyleAttr) {

    val maskRectList = ArrayList<IMaskRect>()

    companion object {
        fun newInstance(context: Context, maskRectList: List<IMaskRect>): MutableGuideView {
            return MutableGuideView(context).apply {
                this.maskRectList.addAll(maskRectList)
            }
        }
    }

    override fun onMask(canvas: Canvas, paint: Paint) {
        maskRectList.forEach {
            canvas.drawRect(it.getRect(), paint)
        }
    }

    override fun onShowDes(tv: TextView) {
        tv.text = "多镂空展示"
    }
}