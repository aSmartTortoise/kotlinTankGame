package business

import enums.Direction
import model.View

interface AutoMoveable: View {

    val currentDirection:Direction
    val velocity:Int

    fun autoMove()
}