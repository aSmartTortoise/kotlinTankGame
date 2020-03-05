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

        var collisionFlag = checkCollision(
            x, y, width, height,
            block.x, block.y, block.width, block.height
        )
        return (if (collisionFlag) currentDirection else null)
    }

    fun notifyCollision(direction: Direction?, block: Blockable?)
}