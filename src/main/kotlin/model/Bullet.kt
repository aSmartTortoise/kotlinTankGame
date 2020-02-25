package model

class Bullet(override var x: Int, override var y: Int) :View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}