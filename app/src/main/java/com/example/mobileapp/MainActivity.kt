package com.example.mobileapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private var isPlayerX = true
    private var gameActive = true
    private val buttons = arrayOfNulls<Button>(9)

    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusTextView)
        val resetBtn = findViewById<Button>(R.id.btnReset)

        for (i in 0..8) {
            val resID = resources.getIdentifier("btn$i", "id", packageName)
            buttons[i] = findViewById(resID)
            buttons[i]?.setOnClickListener {
                onCellClicked(it as Button)
            }
        }

        resetBtn.setOnClickListener { resetGame() }
    }

    private fun onCellClicked(button: Button) {
        if (button.text != "" || !gameActive) return

        if (isPlayerX) {
            button.text = "X"
            button.setTextColor(Color.BLUE)
            statusText.text = "Player O's Turn"
        } else {
            button.text = "O"
            button.setTextColor(Color.RED)
            statusText.text = "Player X's Turn"
        }

        checkWin()
        if (gameActive) isPlayerX = !isPlayerX
    }

    private fun checkWin() {
        for (pos in winningPositions) {
            val val0 = buttons[pos[0]]?.text.toString()
            val val1 = buttons[pos[1]]?.text.toString()
            val val2 = buttons[pos[2]]?.text.toString()

            if (val0 != "" && val0 == val1 && val1 == val2) {
                gameActive = false
                statusText.text = "Player $val0 Wins! 🎉"
                statusText.setTextColor(Color.GREEN)
                return
            }
        }
        if (buttons.all { it?.text != "" }) {
            gameActive = false
            statusText.text = "It's a Draw! 🤝"
        }
    }

    private fun resetGame() {
        for (button in buttons) button?.text = ""
        isPlayerX = true
        gameActive = true
        statusText.text = "Player X's Turn"
        statusText.setTextColor(Color.BLACK)
    }
}