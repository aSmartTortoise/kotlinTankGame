package business

import enums.Direction
import model.View

interface Moveable : View {
    //移动的元素，运动的方向
    val currentDirection: Direction
    //移动的元素，移动的速度
    val velocity: Int

    /**
     * 移动的元素是否将要与阻塞的元素发生碰撞
     * @return 如果为null，说明不会碰撞
     */
    fun willCollision(block: Blockable): Direction? {
        var x: Int = this.x
        var y: Int = this.y
        when (currentDirection) {
            Direction.UP -> y -= velocity
            Direction.DOWN -> y += velocity
            Direction.LEFT -> x -= velocity
            Direction.RIGHT -> x += velocity
        }

        /**
         * 移动物体和窗口边界的检测
         */
        if (y < 0) {
            return Direction.UP
        }else if (y > Config.gameHeight - Config.block) {
            return Direction.DOWN
        }

        if (x < 0) {
           return Direction.LEFT
        }else if (x > Config.gameWidth - Config.block) {
            return Direction.RIGHT
        }
        /**
         * 移动物体和阻挡物体的检测
         */
        var collisionFlag = checkCollision(
            x, y, width, height,
            block.x, block.y, block.width, block.height
        )
        return (if (collisionFlag) currentDirection else null)
    }

    fun notifyCollision(direction: Direction?, block: Blockable?)
}