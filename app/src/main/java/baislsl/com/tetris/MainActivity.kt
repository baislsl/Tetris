package baislsl.com.tetris

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }
}

class MainActivityUI : AnkoComponent<MainActivity> {
    private val style = { v: Any ->
        when (v) {
            is Button -> v.textSize = 26f
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        verticalLayout(theme = R.style.AppTheme) {

            val title = textView(R.string.title) {
                textSize = 30f
                gravity = Gravity.CENTER
                textColor = Color.BLUE
            }

            myButtonView()

            imageView(android.R.drawable.ic_menu_manage).lparams {
                width = wrapContent
                height = wrapContent
                gravity = Gravity.CENTER
            }


        }.applyRecursively { }

    }
}
