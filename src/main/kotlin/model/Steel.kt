package model

import business.Attackable
import business.Blockable
import business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 具有阻挡的能力
 * 遭受打击的能力
 */
class Steel(override val x: Int, override val y: Int) :Blockable, Sufferable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    override val blood: Int = 1

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }

    override fun notifySuffer(attack: Attackable): Array<View>? {
        return null
    }
}