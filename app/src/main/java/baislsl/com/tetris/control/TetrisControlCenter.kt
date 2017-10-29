package baislsl.com.tetris.control

import android.app.AlarmManager
import android.content.Context
import org.jetbrains.anko.toast

open class ControlCenter(val canvas: TetrisCanvas, val keyBoard: TetrisKeyBoard, val context: Context) {
    private val width = canvas.width()
    private val height = canvas.height()

    fun start() {
        context.toast("start game")


        
    }


}