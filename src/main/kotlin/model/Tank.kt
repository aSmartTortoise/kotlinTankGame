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
        if (direction == badDirection) {
            return
        }

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

        //坦克位置越界控制
        if (x < 0) {
            x = 0
        } else if(x > Config.gameWidth - width) {
             x = Config.gameWidth - width
        }

        if (y < 0) {
            y = 0
        }else if(y > Config.gameHeight - height) {
            y = Config.gameHeight - height
        }
    }

    override fun willCollision(block: Blockable): Direction? {
        var x:Int = x
        var y:Int = y

        when(currentDirection) {
            Direction.UP -> y -= velocity
            Direction.DOWN -> y += velocity
            Direction.LEFT -> x -= velocity
            Direction.RIGHT -> x += velocity
        }

        var isCollision:Boolean = when {
            block.x + block.width <= x -> {
                //阻挡物在运动物的左部
                false
            }
            block.y + block.height <= y -> {//阻挡物在运动物的上部
                false
            }
            x + width <= block.x -> {//阻挡物在运动物的右侧
                false
            }
            else -> y + height > block.y
        }

        return if (isCollision) currentDirection else null
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

    open fun shot() {

    }
}