package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index" //key for the key-value pair that will be stored in the bundle

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by viewModels() //new style of ViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        /*Finally, in onCreate(Bundle?), check for this value. If it exists, assign it
        to currentIndex. If a value with the key "index"does not exist in the bundle, or
        if the bundle object is null, set the value to 0.*/
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.isClickable = false
            falseButton.isClickable = false
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            trueButton.isClickable = false
            falseButton.isClickable = false
        }
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }
        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
            //Start CheatActivity
        }
        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    //override onSaveInstanceState(Bundle)to write the value of currentIndex to the bundle with
    //the constant as its key
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}