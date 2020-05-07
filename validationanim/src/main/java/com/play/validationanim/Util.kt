package com.android.validationanim

object Util {

    fun checkForValid(text: String, regEx: String): Boolean {
        return text.matches(Regex(regEx))
    }

}