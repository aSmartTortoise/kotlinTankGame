package business

import enums.Direction
import model.View

interface Moveable:View {
    //移动的元素，运动的方向
    val currentDirection:Direction
    //移动的元素，移动的速度
    val velocity:Int

    /**
     * 移动的元素是否将要与阻塞的元素发生碰撞
     * @return 如果为null，说明不会碰撞
     */
    fun willCollision(block:Blockable):Direction?

    fun notifyCollision(direction: Direction?, block: Blockable?)
}