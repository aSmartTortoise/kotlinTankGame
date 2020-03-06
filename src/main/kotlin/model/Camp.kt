package model

import business.Blockable
import org.itheima.kotlin.game.core.Painter

class Camp(override val x: Int, override val y: Int) : Blockable {
    override val width: Int = Config.block * 2
    override val height: Int = Config.block + Config.block / 2

    override fun draw() {
        var x = this.x
        var y = this.y
        var path: String = "img/steel_small.gif"
        Painter.drawImage(path, x, y)
        x += 32
        Painter.drawImage(path, x, y)
        x += 32
        Painter.drawImage(path, x, y)
        x += 32
        Painter.drawImage(path, x, y)
        x -= 32
        x -= 32
        x -= 32
        y += 32
        Painter.drawImage(path, x, y)
        x += 32
        x += 32
        x += 32
        Painter.drawImage(path, x, y)
        x -= 32
        x -= 32
        x -= 32
        y += 32
        Painter.drawImage(path, x, y)
        x += 32
        x += 32
        x += 32
        Painter.drawImage(path, x, y)
        x -= 32
        x -= 32
        y -= 32
        Painter.drawImage("img/camp.gif", x, y)
    }

}