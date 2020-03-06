package model

import business.*
import enums.Direction
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import java.util.*

/**
 * 敌方坦克
 * 具有移动的能力
 * 自动移动的能力
 * 阻挡的能力
 * 自动射击的能力
 * 遭受攻击的能力
 * 被销毁的能力
 */
class Enemy(override var x: Int, override var y: Int)
    : Moveable, AutoMoveable, Blockable, AutoShot, Sufferable, Destroyable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    override var currentDirection: Direction = Direction.DOWN
    override val velocity: Int = 8
    private var badDirection: Direction? = null
    private var lastShotTime = 0L
    private val shotInterval = 1000L
    private var lastMoveTime = 0L
    private val moveInterval = 50L
    override var blood: Int = 3

    override fun draw() {
        //根据坦克的方向进行绘制
        var path = "img/" + when (currentDirection) {
            Direction.UP -> "enemy_1_u.gif"
            Direction.DOWN -> "enemy_1_d.gif"
            Direction.LEFT -> "enemy_1_l.gif"
            Direction.RIGHT -> "enemy_1_r.gif"
        }
        Painter.drawImage(path, x, y)
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

    override fun autoMove() {
        var currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastMoveTime <= moveInterval) return
        lastMoveTime = currentTimeMillis

        if (currentDirection == badDirection) {
            currentDirection = randomDirection(badDirection)
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

    private fun randomDirection(badDirection: Direction?): Direction {
        var nextInt = Random().nextInt(4)
        var direction = when (nextInt) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }
        if (direction == badDirection) {
            direction = randomDirection(badDirection)
        }
        return direction
    }

    override fun autoShot(): View? {
        var currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastShotTime <= shotInterval) return null
        lastShotTime = currentTimeMillis
        return Bullet(this, currentDirection) { bulletWidth, bulletHeight ->
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

    override fun notifySuffer(attack: Attackable): Array<View>? {
        if (attack.ower is Enemy) {
            //如果是队友发出的攻击，则不受损伤
            return null
        }
        blood -= attack.attackPower
        Composer.play("audio/hit.wav")
        return arrayOf(Blast(x, y))
    }

    override fun canDestroy(): Boolean = blood <= 0
}