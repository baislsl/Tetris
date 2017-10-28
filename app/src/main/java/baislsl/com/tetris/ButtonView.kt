package baislsl.com.tetris

import android.content.Context
import android.util.AttributeSet
import android.view.ViewManager
import android.view.animation.GridLayoutAnimationController
import android.widget.Button
import android.widget.GridLayout
import android.widget.GridView
import android.widget.Toast
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.sdk25.coroutines.onClick

class ButtonView : GridLayout {
    private val customStyle = { v: Any? ->
        when (v) {
            is Button -> v.textSize = 20f
        }
    }

    private fun init() = AnkoContext.createDelegate(this).apply {
        applyRecursively { customStyle }
        rowCount = 3
        columnCount = 3

        val rowSpec: GridLayout.Spec? = GridLayout.spec(2)
        val colSpec: GridLayout.Spec? = GridLayout.spec(2)
        val params = GridLayout.LayoutParams(rowSpec, colSpec)

        button("Left") {
            val row = 1
            val col = 0
            layoutParams =  GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
            onClick {
                toast("button Left")
            }
        }
        button("Right") {
            val row = 1
            val col = 2
            layoutParams =  GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
            onClick {
                toast("button Right")
            }
        }
        button("Top") {
            val row = 0
            val col = 1
            layoutParams =  GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
            onClick {
                toast("button Top")
            }
        }
        button("Down") {
            val row = 2
            val col = 1
            layoutParams =  GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
            onClick {
                toast("button Down")
            }
        }
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}


@Suppress("NOTHING_TO_INLINE")
inline fun ViewManager.myButtonView(theme: Int = R.style.AppTheme) = myButtonView({}, theme)
inline fun ViewManager.myButtonView(init: ButtonView.() -> Unit, theme: Int = 0) = ankoView(::ButtonView, theme, init)