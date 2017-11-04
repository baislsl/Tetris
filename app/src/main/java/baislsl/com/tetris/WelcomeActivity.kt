package baislsl.com.tetris

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WelcomeActivityUi().setContentView(this)
    }

    fun startGame(difficulty: Int) {
        intent = Intent().putExtra(DIFFICULTY, difficulty)
        startActivity<MainActivity>()
    }


}

class WelcomeActivityUi : AnkoComponent<WelcomeActivity> {
    private val style = { v: Any? ->
        when (v) {
            is Button -> v.textSize = 22f
            is TextView -> v.textSize = 30f
        }
    }

    override fun createView(ui: AnkoContext<WelcomeActivity>) = with(ui) {

        verticalLayout(theme = R.style.AppTheme) {
            padding = dip(20)

            textView("Welcome To Tetris Game"){
                textColor = resources.getColor(R.color.foreground_material_light)
            }

            val difficulty = radioGroup {
                radioButton {
                    text = resources.getString(R.string.hard)
                }
                radioButton {
                    text = resources.getString(R.string.medium)
                }
                radioButton {
                    text = resources.getString(R.string.easy)
                }
                check(0)
            }


            button("Start Game") {
                onClick {
                    toast("difficulty = ${difficulty.checkedRadioButtonId}")
                    ui.owner.startGame(difficulty.checkedRadioButtonId)
                }
                backgroundColor = resources.getColor(R.color.material_blue_grey_800)
            }

        }.applyRecursively(style)
    }
}