package baislsl.com.tetris

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import baislsl.com.tetris.control.ControlCenter
import baislsl.com.tetris.control.TetrisCanvas
import baislsl.com.tetris.control.TetrisKeyBoard
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.title)
        val ui = MainActivityUI()
        ui.setContentView(this)
        ControlCenter(ui.canvas, ui.keyBoard, this).start()
    }
}

class MainActivityUI : AnkoComponent<MainActivity> {
    lateinit var canvas : TetrisCanvas
    lateinit var keyBoard : TetrisKeyBoard

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout(theme = R.style.AppTheme) {
            canvas = myCanvasView().getTetrisCanvasDrawer()
            keyBoard = myButtonView().getTetrisKeyBoard()
        }
    }
}
