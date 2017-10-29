package baislsl.com.tetris.control

import android.content.Context
import android.graphics.Point
import android.os.Looper
import android.support.annotation.DrawableRes
import baislsl.com.tetris.R
import org.jetbrains.anko.toast
import java.util.*

enum class Block(var offsets: List<Point>) {
    CUBE(listOf(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1))),
    LINE(listOf(Point(0, 0), Point(0, 1), Point(0, 2), (Point(0, 3))));

    var x: Int = 0
    var y: Int = 0
}


open class ControlCenter(val canvas: TetrisCanvas, val keyBoard: TetrisKeyBoard, val context: Context) {
    private val TAG = "ControlCenter"
    private val width = canvas.width()
    private val height = canvas.height()
    private val timer: Timer = Timer()
    private var gap: Long = 500
    private val solid = R.drawable.white
    private val empty = R.drawable.black
    private var map: Array<Array<Int>> = Array(height, { Array(width, { empty }) })
    private var currentBlock: Block = Block.CUBE


    private fun Point.draw(@DrawableRes drawable: Int) {
        canvas.draw(x, y, drawable)
    }

    private fun Block.clear() {
        offsets.map { Point(x + it.x, y + it.y) }.forEach { it.draw(R.drawable.black) }
    }

    private fun Block.draw() {
        offsets.map { Point(x + it.x, y + it.y) }.forEach { it.draw(R.drawable.white) }
    }

    private fun Block.drop() {
        y += 1
    }

    init {
    }

    fun start() {
        context.toast("start game")
        setUpKeyBoard()
        setUpAnimation()
    }

    private fun setUpKeyBoard() {
        keyBoard.leftButton().setOnClickListener {
            Thread(Runnable {
                Looper.prepare()
                context.toast("enter left button")
                Looper.loop()
            }).start()
        }
    }

    private val timeTask = object : TimerTask() {
        override fun run() {
            val x = currentBlock.x
            val y = currentBlock.y
            if (currentBlock.offsets.any { it.y + y == height - 1 }
                    || currentBlock.offsets.any { map[it.y + y + 1][it.x + x] == solid }) {
                currentBlock.offsets.forEach { map[it.y + y][it.x + x] = solid }

                currentBlock = Block.CUBE
                currentBlock.x =0
                currentBlock.y =0

                canvas.getHandler()?.post{
                    currentBlock.draw()
                }

            } else {
                canvas.getHandler()?.post {
                    currentBlock.clear()
                    currentBlock.drop()
                    currentBlock.draw()
                }
            }
        }
    }

    private fun setUpAnimation() {
        timer.schedule(timeTask, 0, gap)
    }


}