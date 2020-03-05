package model

import business.Destroyable
import org.itheima.kotlin.game.core.Painter

class Blast(override val x: Int, override val y: Int) : Destroyable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    private var imagePaths =  arrayListOf<String>()
    private var index: Int = 0

    init {
        (1..32).forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        var i = index % imagePaths.size
        Painter.drawImage(imagePaths[i], x, y)
        index ++
    }

    override fun canDestroy(): Boolean {
        return index >= imagePaths.size
    }
}