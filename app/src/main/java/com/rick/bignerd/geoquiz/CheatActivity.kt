package com.rick.bignerd.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders


class CheatActivity : AppCompatActivity() {

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProviders.of(this).get(CheatViewModel::class.java)
    }

    private var answer = false
    private lateinit var string: String
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)
        string = intent.getStringExtra(EXTRA_STRING).toString()
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            cheatViewModel.clicks++
            val answerText = when{
                answer -> R.string.true_button
                else -> R.string.false_button
            }
            cheatViewModel.textSet = answerText
            answerTextView.setText(cheatViewModel.textSet!!)
            cheatViewModel.shownIs = true
            setAnswerShownResult(cheatViewModel.shownIs)
            Toast.makeText(this, "all answers are true\nin the book we just make the user\nask for eachh answer for practice\npersonally i would make this much different and practical",
                            Toast.LENGTH_SHORT)
        }

        if (cheatViewModel.clicks > 0){
            cheatViewModel.clicks = 0
            answerTextView.setText(cheatViewModel.textSet!!)
            setAnswerShownResult(cheatViewModel.shownIs)
        }

    }

    companion object{ //a companion allows you to access funs without having an instance of a class
        fun newIntent(packageContext: Context, answer: Boolean, string: String): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answer)
                putExtra(EXTRA_STRING, string)
            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}