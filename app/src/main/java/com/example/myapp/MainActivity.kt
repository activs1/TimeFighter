package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountdown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60

    private val displayMetrics = DisplayMetrics()
    private var height: Int = 0
    private var width: Int = 0



    private lateinit var gameScoreTextView: TextView
    // lateinit - na etapie kompilacji ta zmienna będzie null
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameScoreTextView = findViewById(R.id.game_score_textView)
        timeLeftTextView = findViewById(R.id.time_left_textView)
        tapMeButton = findViewById(R.id.tap_me_button)

        tapMeButton.setOnClickListener { incrementScore() }

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        resetGame()
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun changeButtonPosition() {
        tapMeButton.setX(nextInt(from = 40, until = width -  2 * tapMeButton.width).toFloat())
        tapMeButton.setY(nextInt(from = 60, until = height -  2 * tapMeButton.height).toFloat())

    }

    private fun incrementScore() {
        if (gameStarted){
            score++
            changeButtonPosition()
        }

        else {
            startGame()
        }

        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore
    }

    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score)

        timeLeft = 60
        timeLeftTextView.text = getString(R.string.time_left, timeLeft)

        gameStarted = false

        countDownTimer = object : CountDownTimer(initialCountdown, countDownInterval){

            override fun onTick(millisUntilFinished: Long) {
                timeLeft--
                timeLeftTextView.text = getString(R.string.time_left, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }

    }
}

