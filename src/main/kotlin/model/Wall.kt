package model

import business.Attackable
import business.Blockable
import business.Destroyable
import business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 具有阻挡能力
 * 遭受打击的能力
 * 被销毁的能力
 */
class Wall(override val x: Int, override val y: Int) :Blockable, Sufferable, Destroyable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    override var blood: Int = 3
    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun canDestroy(): Boolean = blood <= 0

    override fun notifySuffer(attack: Attackable) {
        //遭受攻击后，掉血
        blood -= attack.attackPower
    }

}