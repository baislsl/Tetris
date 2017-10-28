package baislsl.com.tetris

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.GridLayout
import baislsl.com.tetris.control.TerisCanvas
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.padding


class CanvasView : GridLayout {
    private val TAG = "CanvasView"
    private var xWidth: Int = 10
    private var yHeight: Int = 10
    private val size: Int = 20
    private var map: Array<Array<Button>> = Array(yHeight, { Array(xWidth, { Button(context) }) })

    private fun init() = AnkoContext.createDelegate(this).apply {
        rowCount = yHeight
        columnCount = xWidth
        padding = 1

        map.forEachIndexed { y, arrayOfButtons ->
            arrayOfButtons.forEachIndexed { x, mapxy ->
                mapxy.backgroundColor = when ((x + y) % 2) {
                    1 -> R.color.c1
                    else -> R.color.c2
                }
                addView(mapxy, GridLayout.LayoutParams(GridLayout.spec(x), GridLayout.spec(y)))
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

    private fun drawxy(x: Int, y: Int, @ColorInt color: Int): Boolean {
        map[x][y].setBackgroundColor(color)
        return true
    }

    fun getTerisCanvasDrawer() = object : TerisCanvas {
        override fun width() = xWidth
        override fun height() = yHeight
        override fun draw(x: Int, y: Int, @ColorInt color: Int) = drawxy(x, y, color)
    }
}

inline fun ViewManager.myCanvasView(theme: Int = R.style.AppTheme) = myCanvasView({}, theme)
inline fun ViewManager.myCanvasView(init: CanvasView.() -> Unit, theme: Int = R.style.AppTheme)
        = ankoView(::CanvasView, theme, init)