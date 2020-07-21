package com.example.guidedemo

import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.guide.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mTestDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tb)

        val targetView = IMaskRect.TargetView(this@MainActivity.btn)
        val rect = IMaskRect.Rect(RectF(10f, 10f, 200f, 300f))
        val rect2 = IMaskRect.Rect(RectF(100f, 100f, 400f, 500f))
        val rect3 = IMaskRect.Rect(RectF(0f, 0f, 0f, 0f))

        btn.setOnClickListener {
            GuideDialog.newInstance().apply {
                // 设置监听
                addChangeListener(guideListener)

                // 添加引导
                addGuideView(TestControllerGuideView.newInstance(this@MainActivity, targetView))
                addGuideView(TestControllerGuideView.newInstance(this@MainActivity, rect))
                addGuideView(CircleGuideView.newInstance(this@MainActivity, targetView))
                // 派大星引导
                addGuideView(StarfishGuideView.newInstance(this@MainActivity, targetView))
                // 添加箭头引导
                addGuideView(
                    HolderGuideView.newInstance(
                        this@MainActivity, targetView, getArrowLayout(this)
                    )
                )

//                addGuideView(
//                    HolderGuideView.newInstance(
//                        this@MainActivity, targetView,
//                        LayoutInflater.from(this@MainActivity)
//                            .inflate(R.layout.guide_holder, null, false) as ConstraintLayout
//                    )
//                )

                // 添加动画引导
                addGuideView(AnimGuideView.newInstance(this@MainActivity, targetView))
                // 添加多镂空引导
                addGuideView(
                    MutableGuideView.newInstance(
                        this@MainActivity,
                        listOf(targetView, rect, rect2)
                    )
                )

                // 打开菜单引导和dialog
                addGuideView(
                    HolderGuideView.newInstance(this@MainActivity, rect3, getDialogLayout(this))
                )

            }.show(supportFragmentManager, "GuideDialog")
        }
    }

    val guideListener = object : GuideDialog.OnChangeListener() {
        override fun onStart() {
            Toast.makeText(this@MainActivity, "开始引导", Toast.LENGTH_SHORT).show()
        }

        override fun onExit() {
            btn.text = "我是一个非常正经的按钮"
            Toast.makeText(this@MainActivity, "退出引导", Toast.LENGTH_SHORT).show()
        }

        override fun onChange(
            preIndex: Int,
            currentIndex: Int
        ) {
            if (currentIndex == 2) {
                btn.text = "点击就送"
            } else {
                btn.text = "我是一个非常正经的按钮"
            }
        }
    }

    private fun getArrowLayout(guide: GuideDialog): ConstraintLayout =
        (LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.guide_arrow, null, false) as ConstraintLayout).apply {
            findViewById<ImageView>(R.id.lav1).setOnClickListener {
                guide.preGuide()
            }
            findViewById<ImageView>(R.id.lav2).setOnClickListener {
                guide.preGuide()
            }
            setOnClickListener {
                guide.nextGuide()
            }
        }

    private fun getDialogLayout(guide: GuideDialog): ConstraintLayout =
        (LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.guide_dialog, null, false) as ConstraintLayout).apply {
            findViewById<Button>(R.id.btnDialog).setOnClickListener {
                mTestDialog = if (mTestDialog == null) {
                    AlertDialog.Builder(this@MainActivity).setTitle("测试").create().apply {
                        window?.setDimAmount(0f)
                        window?.setType(1000)
                        show()
                    }
                } else {
                    mTestDialog?.dismiss()
                    null
                }
            }
            findViewById<Button>(R.id.btnMenu).setOnClickListener {
                if (!tb.showOverflowMenu()) {
                    tb.hideOverflowMenu()
                }
            }
            setOnClickListener {
                guide.nextGuide()
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}