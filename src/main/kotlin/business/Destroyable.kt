package business

import model.View

interface Destroyable:View {

    fun canDestroy():Boolean
}