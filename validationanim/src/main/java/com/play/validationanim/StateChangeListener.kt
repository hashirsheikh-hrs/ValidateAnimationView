package com.play.validationanim

interface StateChangeListener {
    fun onStateChange(state: STATE)
}

inline fun ValidationAnimView.addListener(
    crossinline onTypingState: ()-> Unit = {},
    crossinline onValidState: ()-> Unit = {},
    crossinline onInvalidState: ()-> Unit = {},
    crossinline onClearState: ()-> Unit = {}
): StateChangeListener {
    val stateListener = object : StateChangeListener{
        override fun onStateChange(state: STATE) {
            when(state){
                STATE.TYPING->onTypingState.invoke()
                STATE.INVALID->onValidState.invoke()
                STATE.VALID->onInvalidState.invoke()
                STATE.CLEAR->onClearState.invoke()
            }
        }
    }
    addListener(stateListener)
    return stateListener
}

