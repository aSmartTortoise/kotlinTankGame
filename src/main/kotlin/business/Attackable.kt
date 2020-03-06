package business

import model.View

interface Attackable:View {
    val attackPower: Int//攻击力
    //发射攻击物的物体
    val ower: View
    fun willCollision(suffer: Sufferable): Boolean

    fun notityAttack(suffer: Sufferable)
}