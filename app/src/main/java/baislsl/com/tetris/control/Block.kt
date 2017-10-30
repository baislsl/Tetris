package baislsl.com.tetris.control

import android.graphics.Point
import android.graphics.PointF


operator fun Point.plus(point: Point) = Point(this.x + point.x, this.y + point.y)
fun Point.toFloat() = PointF(this.x.toFloat(), this.y.toFloat())
fun PointF.toInt() = Point(this.x.toInt(), this.y.toInt())
fun PointF.divBy(x: Int, y: Int): PointF = PointF(this.x / x, this.y / y)


interface Block {
    var offsets: List<Point>

    fun adaptTo(canvas: TetrisCanvas): Block = adaptTo(canvas.width() / 2, 0)

    fun adaptTo(x: Int, y: Int): Block {
        offsets.forEach { it.plus(Point(x, y)) }
        return this
    }

    fun tryDrop(): List<Point> = offsets.map { Point(it.x, it.y + 1) }.toList()
    fun tryLeft(): List<Point> = offsets.map { Point(it.x - 1, it.y) }.toList()
    fun tryRight(): List<Point> = offsets.map { Point(it.x + 1, it.y) }.toList()
    fun tryRotate(center: PointF = offsets.reduce(Point::plus).toFloat().divBy(offsets.size, offsets.size)) =
            offsets.map {
                val dx = it.x - center.x
                val dy = it.y - center.y
                PointF(center.x + dy, center.y - dx).toInt()
            }.toList()


    fun transfer(lists: List<Point>): Block {
        this.offsets = lists
        return this
    }

    fun drop(): Block = transfer(tryDrop())
    fun toLeft(): Block = transfer(tryLeft())
    fun toRight(): Block = transfer(tryRight())
    fun rotate(): Block = transfer(tryRotate())

}

class Cube : Block {
    override var offsets: List<Point> = listOf(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1))
}

class Line : Block {
    override var offsets: List<Point> = listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))
}
