package com.example.climbingclubapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var score = 0
    private var currentHold = 0

    private lateinit var textScore: TextView    //declare later
    private lateinit var buttonClimb: Button
    private lateinit var buttonFall: Button
    private lateinit var buttonReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        textScore = findViewById(R.id.text_score)
        buttonClimb = findViewById(R.id.button_climb)
        buttonFall = findViewById(R.id.button_fall)
        buttonReset = findViewById(R.id.button_reset)

        // Set click listeners ( spec ac )
        buttonClimb.setOnClickListener { climb() }
        buttonFall.setOnClickListener { fall() }
        buttonReset.setOnClickListener { reset() }

        // Restore state if available
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score")
            currentHold = savedInstanceState.getInt("currentHold")
            updateScoreDisplay()
        }
    }

    private fun climb() {
        if (currentHold < 9) {
            currentHold++
            score += getPointsForHold(currentHold)
            updateScoreDisplay()
        }
    }

    private fun fall() {
        if (currentHold > 0 && currentHold < 9) {
            score = maxOf(score - 3, 0)
            currentHold = 0
            updateScoreDisplay()
        }
    }

    private fun reset() {
        score = 0
        currentHold = 0
        updateScoreDisplay()
    }

    private fun getPointsForHold(hold: Int): Int {
        return when (hold) {
            in 1..3 -> 1
            in 4..6 -> 2
            in 7..9 -> 3
            else -> 0
        }
    }

    private fun updateScoreDisplay() {
        textScore.text = score.toString()

        val backgroundColor = when (currentHold) {
            in 1..3 -> android.R.color.holo_blue_light
            in 4..6 -> android.R.color.holo_green_light
            in 7..9 -> android.R.color.holo_red_light
            else -> android.R.color.white
        }

        // Update the background color of the root view
        val rootView = findViewById(android.R.id.content) as? View
        rootView?.setBackgroundColor(resources.getColor(backgroundColor, null))

        // Update text color based on the current zone
        when (currentHold) {
            in 1..3 -> textScore.setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
            in 4..6 -> textScore.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
            in 7..9 -> textScore.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
            else -> textScore.setTextColor(resources.getColor(android.R.color.black, null))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("currentHold", currentHold)
    }
}