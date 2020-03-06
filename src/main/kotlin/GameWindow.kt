import business.*
import enums.Direction
import javafx.beans.binding.When
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import model.*
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(
    title = "坦克大战1.0", width = Config.gameWidth,
    height = Config.gameHeight
) {

//    private val views = arrayListOf<View>()
    //线程安全的集合
    private val views = CopyOnWriteArrayList<View>()
    //lateinit 延迟初始化
    private lateinit var tank: Tank

    override fun onCreate() {
        var file = File(javaClass.getResource("/map/1.map").path)
        var rows = file.readLines()
        var rowIndex = 0
        rows.forEach { row ->
            var collumIndex = 0
            row.toCharArray().forEach { collum ->
                when (collum) {
                    '砖' -> views.add(Wall(collumIndex * Config.block, rowIndex * Config.block))
                    '铁' -> views.add(Steel(collumIndex * Config.block, rowIndex * Config.block))
                    '草' -> views.add(Grass(collumIndex * Config.block, rowIndex * Config.block))
                    '水' -> views.add(Water(collumIndex * Config.block, rowIndex * Config.block))
                    '敌' -> views.add(Enemy(collumIndex * Config.block, rowIndex * Config.block))

                }
                collumIndex++
            }
            rowIndex++
        }

        tank = Tank(Config.block * 6, Config.block * 11)
        views.add(tank)
    }

    override fun onDisplay() {
        //界面可见的情况下不停地调用

        views.forEach {
            it.draw()
        }


    }

    override fun onKeyPressed(event: KeyEvent) {
        println("onKeyPressed, wyj code:" + event.code)
        when (event.code) {
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
                var bullet = tank.shot()
                views.add(bullet)
            }
        }
    }

    override fun onRefresh() {
        //界面可见的情况下不停地调用
        //过滤出可运动的元素集合并遍历每个元素
        //进一步遍历阻塞能力的元素集合并遍历每个元素
        views.filter { it is Moveable }.forEach { move ->
            move as Moveable
            var blockView: Blockable? = null
            var badDirection: Direction? = null
            views.filter { (it is Blockable) and (it != move) }.forEach blockTag@{ block ->
                block as Blockable
                var direction = move.willCollision(block)
                direction?.let {
                    badDirection = direction
                    blockView = block
                    return@blockTag
                }
            }
            move.notifyCollision(badDirection, blockView)

        }

        //检测自动移动的物体，并自动移动起来
        views.filter { it is AutoMoveable }.forEach {
            (it as AutoMoveable).autoMove()
        }

        //检测可销毁的物体是否需要销毁
        views.filter { it is Destroyable }.forEach {
            it as Destroyable
            if (it.canDestroy()) {
                views.remove(it)
            }
        }

        //检测具有攻击和遭受攻击能力的物体
        views.filter { it is Attackable }.forEach { attack->
            attack as Attackable
            views.filter { it is Sufferable }.forEach sufferTag@{ suffer->
                suffer as Sufferable
                if (attack.willCollision(suffer)) {
                    attack.notityAttack(suffer)
                    var sufferEffectViews = suffer.notifySuffer(attack)
                    sufferEffectViews?.let {
                        views.addAll(sufferEffectViews)
                    }
                    return@sufferTag
                }
            }
        }
    }
}