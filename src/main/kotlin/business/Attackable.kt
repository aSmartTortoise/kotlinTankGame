package business

import model.View

interface Attackable:View {

    fun willCollision(suffer: Sufferable): Boolean

    fun notityAttack(suffer: Sufferable)
}