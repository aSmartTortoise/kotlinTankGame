package model

import business.Blockable
import business.Moveable
import enums.Direction
import org.itheima.kotlin.game.core.Painter


class Tank(override var x: Int, override var y: Int) :Moveable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override var currentDirection:Direction = Direction.UP
    override val velocity:Int = 8
    private var badDirection:Direction? = null


    override fun draw() {
        //根据坦克的方向进行绘制
        var path = "img/" + when(currentDirection) {
            Direction.UP -> "tank_u.gif"
            Direction.DOWN -> "tank_d.gif"
            Direction.LEFT -> "tank_l.gif"
            Direction.RIGHT -> "tank_r.gif"
        }
        Painter.drawImage(path, x, y)
    }

    fun move(direction:Direction) {
        if (currentDirection != direction) {
            currentDirection = direction
            return
        }

        when(currentDirection) {
            Direction.UP -> y -= velocity
            Direction.DOWN -> y += velocity
            Direction.LEFT -> x -= velocity
            Direction.RIGHT -> x += velocity
        }

        if (y < 0) {
            y = 0
        }else if (y > Config.gameHeight - Config.block) {
            y = Config.gameHeight - Config.block
        }

        if (x < 0) {
            x = 0
        }else if (x > Config.gameWidth - Config.block) {
            x = Config.gameWidth - Config.block
        }




    }

    override fun willCollision(block: Blockable): Direction? {

        return null


    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
    }

    open fun shot() {

    }
}