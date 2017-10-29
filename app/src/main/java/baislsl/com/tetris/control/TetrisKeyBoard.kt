package baislsl.com.tetris.control

import android.widget.Button

interface TetrisKeyBoard {
    fun leftButton() : Button
    fun rightButton() : Button
    fun rotateButton(): Button
    fun speedUpButton(): Button
    fun stopButton(): Button
}