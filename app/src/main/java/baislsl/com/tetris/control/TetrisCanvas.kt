package baislsl.com.tetris.control

import android.graphics.Color
import android.os.Handler
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes

interface TetrisCanvas {
    fun width(): Int
    fun height(): Int
    fun draw(x: Int, y: Int, @DrawableRes drawable: Int): Boolean
    fun getHandler() : Handler?
}