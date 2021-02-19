package com.rick.bignerd.geoquiz
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var currentIndex = -1
    var point = 0
    var isCheater = false
    var cheats = 0
    var cheatedOnThis = false
    var cheatedOnThisID = -1

    val questionList = mutableListOf(
            Question(R.string.question_africa, true),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true),
            Question(R.string.question_mideast, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_europe, true)
    )

    val answerList: MutableList<Boolean> = mutableListOf()
    val cheatedList: MutableList<Boolean> = mutableListOf()

    val currentQuestionAnswer: Boolean
        get() = questionList[currentIndex].answer

    val currentQuestionText: Int
        get() = questionList[currentIndex].textResId

    fun moveToNext(){
        currentIndex++
//        currentIndex = (currentIndex + 1) % questionList.size
        if (currentIndex == questionList.size + 1){

            currentIndex = 0
            point = 0
    }


        Log.i("text", "$currentIndex, ${questionList.size}, ${2 % 5}")
    }

    fun answerFeedback(){
        point++
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionList.size
        if (currentIndex == -2){currentIndex = questionList.size - 2}
        if (currentIndex ==-1) {currentIndex = questionList.size - 1}
    }

    fun updateLists(){
        for (indx in 0 until questionList.size){
            answerList.add(indx, false)
        }

        for (indx in 0 until questionList.size){
            cheatedList.add(indx, false)
        }

    }
}
