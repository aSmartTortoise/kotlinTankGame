package business

import model.View

interface Sufferable:View {

    fun notifySuffer(attack: Attackable)
}