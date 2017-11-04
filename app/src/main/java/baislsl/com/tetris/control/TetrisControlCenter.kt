package baislsl.com.tetris.control

import android.content.Context
import android.graphics.Point
import android.support.annotation.DrawableRes
import android.util.Log
import baislsl.com.tetris.R
import org.jetbrains.anko.toast
import java.util.*


open class ControlCenter(val canvas: TetrisCanvas,
                         val keyBoard: TetrisKeyBoard,
                         val context: Context,
                         difficulty: Int) {
    private val TAG = "ControlCenter"
    private val width = canvas.width()
    private val height = canvas.height()
    private lateinit var timer: Timer
    private val solid = R.drawable.white
    private val empty = R.drawable.black
    private var map: Array<Array<Int>> = Array(height, { Array(width, { empty }) })
    private var currentBlock: Block = Cube().adaptTo(canvas)
    private var isStop = false
    private var gap: Long = when (difficulty) {
        0 -> 300
        1 -> 500
        2 -> 900
        else -> 500
    }


    private fun Point.draw(@DrawableRes drawable: Int) {
        canvas.draw(x, y, drawable)
    }

    private fun Block.clear(): Block {
        offsets.forEach { it.draw(R.drawable.black) }
        return this
    }

    private fun Block.draw(): Block {
        offsets.forEach { it.draw(R.drawable.white) }
        return this
    }

    init {
    }

    private fun move(points: List<Point>): Boolean {
        points.forEach { Log.i(TAG, "x=${it.x}, y=${it.y}, width=${width}, height=${height}") }
        if (points.all {
            it.x in 0 until width
                    && it.y in 0 until height
                    && map[it.y][it.x] == empty
        }) {
            canvas.getHandler()?.post { currentBlock.clear().transfer(points).draw() }
            return true
        }
        return false
    }

    fun start() {
        context.toast("start game")
        setUpKeyBoard()
        setUpAnimation()
    }

    private fun setUpKeyBoard() {
        keyBoard.leftButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            move(currentBlock.tryLeft())
        }

        keyBoard.rightButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            move(currentBlock.tryRight())
        }

        keyBoard.speedUpButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            move(currentBlock.tryDrop())
        }

        keyBoard.rotateButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            move(currentBlock.tryRotate())
        }

        keyBoard.stopButton().setOnClickListener {
            if (isStop) setUpAnimation() else timer.cancel()
            isStop = !isStop
        }
    }


    private fun setUpAnimation() {
        val timeTask = object : TimerTask() {
            override fun run() {
                if (!move(currentBlock.tryDrop())) {
                    currentBlock.offsets.forEach { map[it.y][it.x] = solid }
                    currentBlock = (if (Random().nextInt(2) == 0) Cube() else Line())
                            .adaptTo(canvas)
                    canvas.getHandler()?.post { currentBlock.draw() }
                }
            }
        }

        timer = Timer()
        timer.schedule(timeTask, 0, gap)
    }


}