package com.play.validationanim

import java.lang.Exception

object Util {

    fun checkForValid(text: String, regEx: String): Boolean {
        return try {
            text.matches(Regex(regEx))
        }catch (ex: Exception){
            ex.printStackTrace()
            false
        }
    }

}