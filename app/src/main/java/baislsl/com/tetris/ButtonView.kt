package baislsl.com.tetris

import android.content.Context
import android.util.AttributeSet
import android.view.ViewManager
import android.view.animation.GridLayoutAnimationController
import android.widget.Button
import android.widget.GridLayout
import android.widget.GridView
import android.widget.Toast
import baislsl.com.tetris.control.TetrisKeyBoard
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.sdk25.coroutines.onClick

class ButtonView : GridLayout {
    private var leftBtn: Button = Button(context)
    private var rightBtn: Button = Button(context)
    private var rotateBtn: Button = Button(context)
    private var speedUpBtn: Button = Button(context)
    private var stopBtn: Button = Button(context)


    fun getTetrisKeyBoard() = object : TetrisKeyBoard {
        override fun leftButton() = leftBtn
        override fun rightButton() = rightBtn
        override fun rotateButton() = rotateBtn
        override fun speedUpButton() = speedUpBtn
        override fun stopButton() = stopBtn
    }

    private fun init() = AnkoContext.createDelegate(this).apply {
        rowCount = 3
        columnCount = 4

        leftBtn = button("Left") {
            val row = 1
            val col = 0
            layoutParams = GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
        }
        rightBtn = button("Right") {
            val row = 1
            val col = 2
            layoutParams = GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
        }
        rotateBtn = button("Rotate") {
            val row = 0
            val col = 1
            layoutParams = GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
        }
        speedUpBtn = button("Speed") {
            val row = 2
            val col = 1
            layoutParams = GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
        }

        stopBtn = button("Stop") {
            val row = 0
            val col = 3
            layoutParams = GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col))
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