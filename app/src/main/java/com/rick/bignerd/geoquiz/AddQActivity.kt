package com.rick.bignerd.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

class AddQActivity : AppCompatActivity() {

    lateinit var etAddQuestion: TextView
    lateinit var btnAddQuestion: Button
    lateinit var questionAdded: String

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_q)

        etAddQuestion = findViewById(R.id.etAddQuestion)
        btnAddQuestion = findViewById(R.id.btnAddQuestion)

//        questionAdded = etAddQuestion.text.toString()
//        val question = Question(0, false, questionAdded)

        btnAddQuestion.setOnClickListener {
//            if (questionAdded.isNotEmpty()){
//                quizViewModel.questionList.add(question)
//            }
            Toast.makeText(this, "Espera update", Toast.LENGTH_LONG).show()
        }
    }
}