package model

import business.AutoMoveable
import business.Destroyable
import enums.Direction
import org.itheima.kotlin.game.core.Painter

class Bullet(override var currentDirection: Direction, creator: (width:Int, height: Int) -> Pair<Int, Int>):
    AutoMoveable, Destroyable {

    override var x: Int = 0
    override var y: Int = 0
    override val width: Int
    override val height: Int
    override val velocity: Int = 8
    private var path: String = when (currentDirection) {
        Direction.UP -> "img/bullet_u.gif"
        Direction.DOWN -> "img/bullet_d.gif"
        Direction.LEFT -> "img/bullet_l.gif"
        Direction.RIGHT -> "img/bullet_r.gif"
    }

    init {
        var size = Painter.size(path)
        width = size[0]
        height = size[1]
        var pair = creator.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun draw() {
        Painter.drawImage(path, x, y)
    }

    override fun autoMove() {
        when(currentDirection) {
            Direction.UP -> y -= velocity
            Direction.DOWN -> y += velocity
            Direction.LEFT -> x -= velocity
            Direction.RIGHT -> x += velocity
        }
    }

    override fun canDestroy(): Boolean {
        if (x < width || x > Config.gameWidth) {
            return true
        }
        if (y < height || y > Config.gameHeight) {
            return true
        }
        return false
    }
}