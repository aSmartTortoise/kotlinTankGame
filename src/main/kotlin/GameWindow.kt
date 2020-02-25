import business.Blockable
import business.Moveable
import enums.Direction
import javafx.beans.binding.When
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import model.*
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.io.File

class GameWindow:Window(title = "坦克大战1.0", width = Config.gameWidth,
    height = Config.gameHeight) {

    private val views = arrayListOf<View>()
    //lateinit 延迟初始化
    private lateinit var tank:Tank

    override fun onCreate() {
        var file = File(javaClass.getResource("/map/1.map").path)
        var rows:List<String> = file.readLines()
        var rowIndex = 0
        rows.forEach { row->
            var collumIndex = 0
            row.toCharArray().forEach { collum->
                when(collum) {
                    '砖' -> views.add(Wall(collumIndex * Config.block, rowIndex * Config.block))
                    '铁' -> views.add(Steel(collumIndex * Config.block, rowIndex * Config.block))
                    '草' -> views.add(Grass(collumIndex * Config.block, rowIndex * Config.block))
                    '水' -> views.add(Water(collumIndex * Config.block, rowIndex * Config.block))

                }
                collumIndex ++
            }
            rowIndex++
        }

        tank = Tank(Config.block * 6, Config.block * 10)
        views.add(tank)
    }

    override fun onDisplay() {

        views.forEach {
            it.draw()
        }

    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code) {
            KeyCode.W -> {
                tank.move(Direction.UP)
            }
            KeyCode.S -> {
                tank.move(Direction.DOWN)
            }
            KeyCode.A -> {
                tank.move(Direction.LEFT)
            }
            KeyCode.D -> {
                tank.move(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                tank.shot()
            }
        }
    }

    override fun onRefresh() {
        //过滤出可运动的元素集合并遍历每个元素
        //进一步遍历阻塞能力的元素集合并遍历每个元素
        views.filter { it is Moveable }.forEach {move ->
            move as Moveable
            var badDirection: Direction? = null
            var badBlock:Blockable? = null
            views.filter { it is Blockable }.forEach blockTag@ { block->
                block as Blockable
                var direction = move.willCollision(block)
                direction?.let {
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }

            move.notifyCollision(badDirection, badBlock)
        }
    }
}