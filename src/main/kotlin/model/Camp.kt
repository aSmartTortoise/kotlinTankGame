package model

import business.Attackable
import business.Blockable
import business.Destroyable
import business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/**
 * 具有阻挡的能力
 * 遭受挨打的能力
 * 被销毁的能力
 */
class Camp(override var x: Int, override var y: Int) : Blockable, Sufferable, Destroyable {
    override var width: Int = Config.block * 2
    override var height: Int = Config.block + Config.block / 2

    override var blood: Int = 12

    override fun draw() {
        var path: String = "img/steel_small.gif"
        if (blood <= 3) {
            this.x = (Config.gameWidth - Config.block) / 2
            this.y = Config.gameHeight - Config.block
            width = Config.block
            height = Config.block

            Painter.drawImage("img/camp.gif", x, y)
        } else {
            var x = this.x
            var y = this.y
            if (blood <= 6) {
                path = "img/wall_small.gif"
            }
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

    override fun notifySuffer(attack: Attackable): Array<View>? {
        blood -= attack.attackPower
        Composer.play("audio/hit.wav")
        if (blood == 3 || blood == 6) {
            val x = x - 32
            val y = y - 32
            return arrayOf(Blast(x, y)
                , Blast(x + 32, y)
                , Blast(x + Config.block, y)
                , Blast(x + Config.block + 32, y)
                , Blast(x + Config.block * 2, y)
                , Blast(x, y + 32)
                , Blast(x, y + Config.block)
                , Blast(x, y + Config.block + 32)
                , Blast(x + Config.block * 2, y + 32)
                , Blast(x + Config.block * 2, y + Config.block)
                , Blast(x + Config.block * 2, y + Config.block + 32))
        }
        return null
    }

    override fun canDestroy(): Boolean = blood <= 0

    override fun showDead(): Array<View>? {
        return arrayOf(
            Blast(x - 32, y - 32)
            , Blast(x, y - 32)
            , Blast(x + 32, y - 32)

            , Blast(x - 32, y)
            , Blast(x, y)
            , Blast(x + 32, y)

            , Blast(x - 32, y + 32)
            , Blast(x, y + 32)
            , Blast(x + 32, y + 32)
        )
    }

}