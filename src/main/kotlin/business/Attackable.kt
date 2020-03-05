package business

import model.View

interface Attackable:View {
    val attackPower: Int//攻击力
    fun willCollision(suffer: Sufferable): Boolean

    fun notityAttack(suffer: Sufferable)
}