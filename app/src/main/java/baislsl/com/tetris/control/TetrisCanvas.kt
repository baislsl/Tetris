package baislsl.com.tetris.control

import android.graphics.Color
import android.support.annotation.ColorInt

interface TerisCanvas {
    fun width(): Int
    fun height(): Int
    fun draw(x: Int, y: Int, @ColorInt color: Int): Boolean
}