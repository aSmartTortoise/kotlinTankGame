package business

import model.View

interface Destroyable:View {

    fun canDestroy():Boolean

    fun showDead(): Array<View>? {
        return null
    }
}