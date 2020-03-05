package model

import business.Attackable
import business.Blockable
import business.Sufferable
import org.itheima.kotlin.game.core.Painter


class Wall(override val x: Int, override val y: Int) :Blockable, Sufferable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notifySuffer(attack: Attackable) {
    }

}