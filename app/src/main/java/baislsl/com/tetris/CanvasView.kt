package baislsl.com.tetris

import android.content.Context
import android.os.Handler
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.ViewManager
import android.widget.GridLayout
import android.widget.ImageButton
import baislsl.com.tetris.control.TetrisCanvas
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView


class CanvasView : GridLayout {
    private val TAG = "CanvasView"
    private var xCount: Int = 10
    private var yCount: Int = 16
    private var size: Int = 100
    private var map: Array<Array<ImageButton>> = Array(yCount, { Array(xCount, { ImageButton(context) }) })

    private fun init() = AnkoContext.createDelegate(this).apply {
        rowCount = yCount
        columnCount = xCount
        padding = 0

        map.forEachIndexed { y, arrayOfButtons ->
            arrayOfButtons.forEachIndexed { x, buttonxy ->
                val params = GridLayout.LayoutParams(GridLayout.spec(y), GridLayout.spec(x))
                params.height = size
                params.width = size
                addView(buttonxy, params)

                drawxy(x, y, R.drawable.black)

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

    private fun drawxy(x: Int, y: Int, @DrawableRes drawable: Int): Boolean {
        map[y][x].image = resources.getDrawable(drawable)
        return true
    }

    fun getTetrisCanvasDrawer() = object : TetrisCanvas {
        override fun width() = xCount
        override fun height() = yCount
        override fun draw(x: Int, y: Int, @DrawableRes drawable: Int) = drawxy(x, y, drawable)
        override fun getHandler(): Handler? = handler
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun ViewManager.myCanvasView(theme: Int = R.style.AppTheme) = myCanvasView({}, theme)
inline fun ViewManager.myCanvasView(init: CanvasView.() -> Unit, theme: Int = R.style.AppTheme)
        = ankoView(::CanvasView, theme, init)