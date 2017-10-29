package baislsl.com.tetris.control

import android.content.Context
import android.graphics.Point
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
    private lateinit var timer: Timer
    private var gap: Long = 500
    private val solid = R.drawable.white
    private val empty = R.drawable.black
    private var map: Array<Array<Int>> = Array(height, { Array(width, { empty }) })
    private var currentBlock: Block = Block.CUBE
    private var isStop = false


    private fun Point.draw(@DrawableRes drawable: Int) {
        canvas.draw(x, y, drawable)
    }

    private fun Block.clear(): Block {
        offsets.map { Point(x + it.x, y + it.y) }.forEach { it.draw(R.drawable.black) }
        return this
    }

    private fun Block.draw(): Block {
        offsets.map { Point(x + it.x, y + it.y) }.forEach { it.draw(R.drawable.white) }
        return this
    }

    private fun Block.drop(): Block {
        y += 1
        return this
    }

    private fun Block.moveLeft(): Block {
        x -= 1
        return this
    }

    private fun Block.moveRight(): Block {
        x += 1
        return this
    }

    private fun Block.setOffsets(offsets: List<Point>): Block {
        this.offsets = offsets
        return this
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
            if (isStop) return@setOnClickListener
            val x = currentBlock.x
            val y = currentBlock.y
            if (currentBlock.offsets.all { it.x + currentBlock.x - 1 >= 0 }
                    && currentBlock.offsets.all { map[it.y + y][it.x + x - 1] == empty }) {
                canvas.getHandler()?.post {
                    currentBlock.clear().moveLeft().draw()
                }
            }
        }

        keyBoard.rightButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            val x = currentBlock.x
            val y = currentBlock.y
            if (currentBlock.offsets.all { it.x + x + 1 < width }
                    && currentBlock.offsets.all { map[it.y + y][it.x + x + 1] == empty }) {
                canvas.getHandler()?.post {
                    currentBlock.clear().moveRight().draw()
                }
            }
        }

        keyBoard.speedUpButton().setOnClickListener {
            if (isStop) return@setOnClickListener
            val x = currentBlock.x
            val y = currentBlock.y
            if (currentBlock.offsets.all { it.y + y + 1 < height }
                    && currentBlock.offsets.all { map[it.y + y + 1][it.x + x] == empty }) {
                canvas.getHandler()?.post {
                    currentBlock.clear().drop().draw()
                }
            }
        }

        keyBoard.rotateButton().setOnClickListener {
            if (isStop) return@setOnClickListener

            val x = currentBlock.x
            val y = currentBlock.y
            val center = currentBlock.offsets[0]
            val newXY = currentBlock.offsets.map {
                val dis = Point(it.x - center.x, it.y - center.y)
                Point(-dis.y + center.x, dis.x + center.y)
            }.toList()

            if (newXY.all {
                it.x + x in 0..width && it.y + y in 0..height && map[it.y + y][it.x + x] == empty
            }) {
                canvas.getHandler()?.post {
                    currentBlock.clear().setOffsets(newXY).draw()
                }
            }
        }

        keyBoard.stopButton().setOnClickListener {
            if (isStop) setUpAnimation() else timer.cancel()
            isStop = !isStop
        }
    }


    private fun setUpAnimation() {
        val timeTask = object : TimerTask() {
            override fun run() {
                val x = currentBlock.x
                val y = currentBlock.y
                if (currentBlock.offsets.any { it.y + y + 1 >= height }
                        || currentBlock.offsets.any { map[it.y + y + 1][it.x + x] == solid }) {
                    currentBlock.offsets.forEach { map[it.y + y][it.x + x] = solid }

                    currentBlock = if (Random().nextInt(2) == 0) Block.CUBE else Block.LINE
                    currentBlock.x = 0
                    currentBlock.y = 0

                    canvas.getHandler()?.post {
                        currentBlock.draw()
                    }

                } else {
                    canvas.getHandler()?.post {
                        currentBlock.clear().drop().draw()
                    }
                }
            }
        }

        timer = Timer()
        timer.schedule(timeTask, 0, gap)
    }


}