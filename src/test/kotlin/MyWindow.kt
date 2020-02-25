import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

class MyWindow:Window() {
    override fun onCreate() {
        println("创建窗体")
    }

    override fun onDisplay() {
        Painter.drawImage("img/selecttank.gif", 0, 0)
        Painter.drawColor(Color.RED, 100, 50, 100, 100)
        Painter.drawText("hello guys,", 100, 100)



    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code) {
            KeyCode.ENTER -> println("点击了回车键")
            KeyCode.L -> Composer.play("audio/blast.wav")
        }

    }

    override fun onRefresh() {
        //处理业务逻辑的，该处理是耗时的
    }
}

fun main() {
    Application.launch(MyWindow::class.java)
}