package com.rick.bignerd.geoquiz

import androidx.annotation.StringRes

data class Question (
    @StringRes val textResId: Int,
    val answer: Boolean,
    var texto: String? = null
)
