package model

import business.Blockable
import business.Moveable
import enums.Direction
import org.itheima.kotlin.game.core.Painter


class Tank(override var x: Int, override var y: Int) : Moveable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override var currentDirection: Direction = Direction.UP
    override val velocity: Int = 8
    private var badDirection: Direction? = null


    override fun draw() {
        //根据坦克的方向进行绘制
        var path = "img/" + when (currentDirection) {
            Direction.UP -> "tank_u.gif"
            Direction.DOWN -> "tank_d.gif"
            Direction.LEFT -> "tank_l.gif"
            Direction.RIGHT -> "tank_r.gif"
        }
        Painter.drawImage(path, x, y)
    }

    fun move(direction: Direction) {
        //如果即将发生碰撞
        if (direction == badDirection) {
            return
        }

        //如果当前坦克的朝向和待移动的方向不一致时候，只改变坦克的朝向，坦克不移动
        if (currentDirection != direction) {
            currentDirection = direction
            return
        }

        when (currentDirection) {
            Direction.UP -> y -= velocity
            Direction.DOWN -> y += velocity
            Direction.LEFT -> x -= velocity
            Direction.RIGHT -> x += velocity
        }

        if (y < 0) {
            y = 0
        } else if (y > Config.gameHeight - Config.block) {
            y = Config.gameHeight - Config.block
        }

        if (x < 0) {
            x = 0
        } else if (x > Config.gameWidth - Config.block) {
            x = Config.gameWidth - Config.block
        }
    }


    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction

    }

    open fun shot(): Bullet {
        return Bullet(currentDirection) { bulletWidth, bulletHeight ->
            /**
             * 坦克朝上
             * bulletX = tankX + (tankWidth-bulletWidth)/2
             * bulletY = tankY - bulletHeight/2
             */
            var bulletX: Int = x
            var bulletY: Int = y
            when (currentDirection) {
                Direction.UP -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y + height - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = x - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = x + width - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
            }
            Pair(bulletX, bulletY)
        }
    }
}