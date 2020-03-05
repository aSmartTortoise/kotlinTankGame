package business

import model.View

interface Sufferable:View {

    val blood:Int
    /**
     * 遭受打击的回调方法
     * @return 遭受打击后的一个表现效果的View
     */
    fun notifySuffer(attack: Attackable): Array<View>?
}