package model

import business.Attackable
import business.AutoMoveable
import business.Destroyable
import business.Sufferable
import enums.Direction
import extend.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * 具备自动移动的能力
 * 可以被销毁的能力
 * 攻击的能力
 */
class Bullet(override var currentDirection: Direction, creator: (width:Int, height: Int) -> Pair<Int, Int>):
    AutoMoveable, Destroyable, Attackable {

    override var x: Int = 0
    override var y: Int = 0
    override val width: Int
    override val height: Int
    override val velocity: Int = 8
    private var destroyed: Boolean = false
    override val attackPower: Int = 1
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
        if (destroyed) return true
        if (x < -width || x > Config.gameWidth) {
            return true
        }
        if (y < -height || y > Config.gameHeight) {
            return true
        }
        return false
    }

    override fun willCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notityAttack(suffer: Sufferable) {
        //子弹打击物体，子弹即销毁
        destroyed = true
    }
}