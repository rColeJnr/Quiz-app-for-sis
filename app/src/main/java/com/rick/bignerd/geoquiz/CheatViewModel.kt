package com.rick.bignerd.geoquiz

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER = "com.rick.bigranch.geoquiz.extraAnswer"
const val EXTRA_STRING = "com.rick.bigranch.geoquiz.extraString"
const val EXTRA_ANSWER_SHOWN = "com.rick.bigranch.geoquiz.extraAnswerShown"

class CheatViewModel: ViewModel() {

    var shownIs = false
    var textSet: Int? = null
    var clicks: Int = 0
    var cheats: Int = 0

}