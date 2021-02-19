package com.rick.bignerd.geoquiz

import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rick.bignerd.geoquiz.CheatActivity.Companion.newIntent

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_ACTIVITY = 0

class GeoQuizActivity : AppCompatActivity() {

    lateinit var trueButton: Button
    lateinit var falseButton: Button
    lateinit var nextButton: Button
    lateinit var prevButton: Button
    lateinit var addButton: Button
    lateinit var tvQuestion: TextView
    lateinit var apiTv: TextView

//    val answerList: MutableList<Boolean> = mutableListOf()
//    val cheatedList: MutableList<Boolean> = mutableListOf()
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geoquiz)


        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, -1) ?: -1
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        addButton = findViewById(R.id.add_button)
        tvQuestion = findViewById(R.id.tvQuestion)
        apiTv = findViewById(R.id.apiTv)

        quizViewModel.updateLists()

        tvQuestion.isEnabled = false
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        addButton.visibility = View.INVISIBLE


        updateAtCreateon()

        apiTv.text = "API Level: ${Integer.valueOf(android.os.Build.VERSION.SDK_INT).toString()}"

        trueButton.setOnClickListener {
            var answer = true
                trueButton.isClickable = false
                if (quizViewModel.cheatedOnThisID != -1){
                    quizViewModel.isCheater = false
                }
                checkAnswer(answer)
        }

        falseButton.setOnClickListener {
            var answer = false
                falseButton.isClickable = false
                if (quizViewModel.cheatedOnThisID != -1){
                    quizViewModel.isCheater = false
                }
                checkAnswer(answer)

        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            nextQuestion()
        }

        if (quizViewModel.currentIndex <= -1){
            prevButton.visibility = View.INVISIBLE
        }
        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
            prevQuestion()
        }

        tvQuestion.setOnClickListener {
            quizViewModel.cheats++
            countCheats()
            val currentIndex = quizViewModel.currentIndex
//            quizViewModel.cheatedOnThisID = currentIndex
            val answer = quizViewModel.questionList[currentIndex].answer
            val string = "string"
            val myIntent = CheatActivity.newIntent(this, answer, string)
            startActivityForResult(myIntent, REQUEST_CODE_ACTIVITY) //start activity, give an id, and ask for a result from the activity
            Log.i("text now", "${quizViewModel.cheatedOnThisID}")
        }

        addButton.setOnClickListener {
            val myIntent = Intent(this, AddQActivity::class.java)
            startActivity(myIntent)
        }
    }

    fun checkAnswer(answer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (answer == correctAnswer){ quizViewModel.answerFeedback()}
        val messageResId = when{
            quizViewModel.cheatedList[quizViewModel.currentIndex] -> R.string.judgment_toast
            answer == correctAnswer -> R.string.answered_toast
            else -> R.string.answered_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

    fun nextQuestion(){
        val questionList = quizViewModel.questionList
        trueButton.isClickable = true
        falseButton.isClickable = true
        if (quizViewModel.currentIndex == 0){
            tvQuestion.isClickable = true
            tvQuestion.isEnabled = true
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            addButton.visibility = View.INVISIBLE
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
        if (quizViewModel.currentIndex < questionList.size) {
            nextButton.text = "Next >"
            updateQuestion()
        }
        if (quizViewModel.currentIndex == 1){prevButton.isEnabled = true; prevButton.visibility = View.VISIBLE}
        if (quizViewModel.currentIndex == questionList.size-1) {
            updateQuestion()
            nextButton.text = "Result"
        }
        if (quizViewModel.currentIndex == questionList.size){

            tvQuestion.isClickable = false

            prevButton.visibility = View.INVISIBLE
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
            addButton.visibility = View.VISIBLE

            nextButton.setText("Restart")
            tvQuestion.text = "you've reached the end of the quiz\npress restart to play again\nyou scored ${quizViewModel.point} points"
            Log.i("text", "$quizViewModel.currentIndex, ${questionList.size}, ${2 % 5}")
        }
        if (quizViewModel.currentIndex == questionList.size + 1){

            nextButton.text = "Next >"
            updateQuestion()
            addButton.visibility = View.INVISIBLE
            quizViewModel.cheats = 0

            prevButton.visibility = View.INVISIBLE
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
        Log.i("text", "${quizViewModel.currentIndex}, ${questionList.size}, ${2 % 5}")
    }

    fun prevQuestion(){
        if (quizViewModel.currentIndex == 0){
            prevButton.isEnabled = false
        }
        nextButton.text = "Next >"

    }
    fun updateQuestion(){
        val questionTextResId =  quizViewModel.currentQuestionText
        tvQuestion.setText(questionTextResId)
    }

    fun countCheats() {
        if (quizViewModel.cheats == 3) {
            tvQuestion.isClickable = false
            Toast.makeText(this, "No more cheats", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAtCreateon() {
        val currentIndex = quizViewModel.currentIndex
        val questionList = quizViewModel.questionList
        if (currentIndex >= 0) {
            if (currentIndex == questionList.size) {
                tvQuestion.text = "you've reached the end of the quiz\npress restart to play again\nyou scored ${quizViewModel.point} points"
            } else {
                addButton.visibility = View.INVISIBLE
                updateQuestion()
            }
            nextButton.text = "Next >"
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        if (currentIndex == questionList.size){
            tvQuestion.isClickable = false
            nextButton.text = "Restart"
            addButton.visibility = View.VISIBLE
        }
        if (currentIndex == questionList.size - 1){
            nextButton.text = "Result"
        }
        if (currentIndex == 0){
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            prevButton.isEnabled = false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }

        if (requestCode == REQUEST_CODE_ACTIVITY){
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.cheatedList[quizViewModel.currentIndex] = quizViewModel.isCheater
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

}