package business

import model.View

interface Sufferable:View {

    val blood:Int

    fun notifySuffer(attack: Attackable)
}