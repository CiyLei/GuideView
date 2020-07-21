package com.example.guide

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

/**
 * Create by ChenLei on 2020/7/13
 * Describe: 引导页Dialog
 */
open class GuideDialog : DialogFragment(), IGuideController {

    companion object {
        fun newInstance(): GuideDialog {
            return GuideDialog()
        }
    }

    // 根布局
    private var mRootLayout: GuideRootView? = null

    // 引导列表
    private val mGuideViewList = ArrayList<GuideView>()

    // 引导页当前索引
    private var mGuideViewIndex = -1

    // 引导页回调
    private val mOnListenerList = ArrayList<OnChangeListener>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(context!!, R.style.transparent_dialog).apply {
            mRootLayout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_guide, null, false) as? GuideRootView
            setContentView(mRootLayout!!)
            // 设置应用内最大层级
//            window?.setType(WindowManager.LayoutParams.LAST_SUB_WINDOW)
            // 设置状态栏沉浸效果
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
            } else {
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                );
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                );
            }
        }
    }

    /**
     * 开始引导
     */
    override fun onStart() {
        super.onStart()
        mOnListenerList.forEach {
            it.onStart()
        }
        nextGuide()
    }

    /**
     * 添加引导
     */
    fun addGuideView(guideView: GuideView) {
        mGuideViewList.add(guideView)
    }

    /**
     * 下一个引导
     */
    override fun nextGuide() {
        if (mGuideViewIndex + 1 < mGuideViewList.size) {
            showGuide(mGuideViewIndex, ++mGuideViewIndex)
        } else {
            dismiss()
        }
    }

    /**
     * 上一个引导
     */
    override fun preGuide() {
        if (mGuideViewIndex > 0) {
            showGuide(mGuideViewIndex, --mGuideViewIndex)
        } else {
            dismiss()
        }
    }

    override fun onDestroy() {
        mOnListenerList.forEach {
            it.onExit()
        }
        mOnListenerList.clear()
        super.onDestroy()
    }

    /**
     * 显示当前引导
     */
    private fun showGuide(preIndex: Int, currentIndex: Int) {
        // 触发回调
        mOnListenerList.forEach {
            it.onChange(preIndex, currentIndex)
        }
        // 删除跟布局所有子View
        mRootLayout?.removeAllViews()
        // 添加当前的引导
        mRootLayout?.addView(mGuideViewList[currentIndex].apply {
            guideController = this@GuideDialog
        })
    }

    /**
     * 添加监听
     */
    fun addChangeListener(changeListener: OnChangeListener) =
        mOnListenerList.add(changeListener)

    /**
     * 移除监听
     */
    fun removeChangeListener(changeListener: OnChangeListener) =
        mOnListenerList.remove(changeListener)

    /**
     * 引导页回调
     */
    abstract class OnChangeListener {
        /**
         * 开始引导
         */
        open fun onStart() {

        }

        /**
         * 结束引导
         */
        open fun onExit() {

        }

        /**
         * 引导页改变
         */
        open fun onChange(preIndex: Int, currentIndex: Int) {

        }
    }
}