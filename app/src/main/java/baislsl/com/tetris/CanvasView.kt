package baislsl.com.tetris

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

class CanvasView : View {
    private val TAG = "CanvasView"
    private var xWidth: Int = 0
    private var yHeight: Int = 0
    private val size: Int = 20
    private var map: Array<Array<Paint>> = Array(size, { Array(size, { Paint() }) })

    private fun init() {
        for ((y, line) in map.withIndex()) {
            for ((x, paint) in line.withIndex()) {
                paint.color = if ((x + y) % 2 == 0) Color.WHITE else Color.BLACK
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        super.onDraw(canvas)

        val width = canvas!!.width
        val height = canvas.height
        xWidth = (width * 1.0 / size).toInt()
        yHeight = (height * 1.0 / size).toInt()

        Log.i(TAG, "canvas width = " + width)
        Log.i(TAG, "canvas height = " + height)
        Log.i(TAG, "x width = " + xWidth)
        Log.i(TAG, "y height = " + yHeight)
        Log.i(TAG, "block size = " + size.toString())

        val sizeF = size.toFloat()
        for ((y, line) in map.withIndex()) {
            for ((x, paint) in line.withIndex()) {
                canvas.drawRect(x * sizeF, y * sizeF, x * sizeF + sizeF, y * sizeF + sizeF,
                        paint)
            }
        }
    }
}

inline fun ViewManager.myCanvasView(theme: Int = R.style.AppTheme) = myCanvasView({}, theme)
inline fun ViewManager.myCanvasView(init: CanvasView.() -> Unit, theme: Int = R.style.AppTheme)
        = ankoView(::CanvasView, theme, init)