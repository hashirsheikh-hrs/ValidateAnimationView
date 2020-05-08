package com.play.validationanim

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.play.validationanim.Util.checkForValid


class ValidationAnimView : View {
    private val delay = 500L
    private var typedInput = ""
    private var lastEditText = System.currentTimeMillis()

    private val STATE_TYPING = intArrayOf(R.attr.state_typing)
    private val STATE_TICK = intArrayOf(R.attr.state_tick)
    private val STATE_CROSS = intArrayOf(R.attr.state_cross)
    private val STATE_CLEAR = intArrayOf(R.attr.state_clear)

    private var lastState = STATE.CLEAR

    var typingState = false
        set(value) {
            refreshDrawableState()
            field = value
        }

    var validState = false
        set(value) {
            refreshDrawableState()
            field = value
        }
    var invalidState = false
        set(value) {
            refreshDrawableState()
            field = value
        }
    var clearState = true
        set(value) {
            refreshDrawableState()
            field = value
        }

    private var textViewId: Int = -1
    private var editText: EditText? = null
    private var regexPattern: String? = null
    private var predicate: ((String) -> Boolean)? = null

    val typingRunnable = Runnable {
        if (System.currentTimeMillis() > (lastEditText + delay - 500)) {
            performAction()
        }
    }

    constructor(context: Context?) : super(context) {
        //ignored
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }
        val t: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ValidationAnimView)
        textViewId = t.getResourceId(R.styleable.ValidationAnimView_editTextRef, NO_ID)
        regexPattern = t.getString(R.styleable.ValidationAnimView_patternToMatch) ?: null
        t.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg))
        } else {
            background = ContextCompat.getDrawable(context, R.drawable.bg)
        }
        if (editText == null && textViewId != -1) {
            rootView?.findViewById<View>(textViewId)?.let {
                if (it is EditText) setUpWithEditText(it)
                if (regexPattern != null) registerPattern(regexPattern!!)
            }
        }
    }

    /**
     * Register edittext on behalf of this view
     * @param editText EditText
     */
    fun setUpWithEditText(editText: EditText) {

        editText.doAfterTextChanged {
            val s = it ?: return@doAfterTextChanged
            if (s.isNotEmpty()) {
                lastEditText = System.currentTimeMillis()
                typedInput = s.toString()
                typing()
                postDelayed(typingRunnable, delay)
            } else {
                clear()
            }
        }

        editText.doOnTextChanged { _, _, _, _ ->
            removeCallbacks(typingRunnable)
        }
/*
        editText.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                removeCallbacks(typingRunnable)
            }
        ) {
            val s = it ?: return@addTextChangedListener
            if (s.isNotEmpty()) {
                lastEditText = System.currentTimeMillis()
                typedInput = s.toString()
                typing()
                postDelayed(typingRunnable, delay)
            } else {
                clear()
            }
        }*/
    }

    /**
     * this will reset the predicate
     * @param regex String
     */
    fun registerPattern(regex: String) {
        this.regexPattern = regex
        predicate = null
    }

    /**
     * this will reset the pattern
     * @param predicate Predicate<String>
     */
    fun registerPredicate(predicate: (String) -> Boolean) {
        this.predicate = predicate
        this.regexPattern = null
    }

    private fun typing() {
        listener?.onStateChange(STATE.TYPING)
        if (lastState == STATE.TYPING) return
        lastState = STATE.TYPING
        changeValues(typingState = true)
    }

    private fun changeValues(
        typingState: Boolean = false,
        validState: Boolean = false,
        invalidState: Boolean = false,
        clearState: Boolean = false
    ) {
        this.typingState = typingState
        this.validState = validState
        this.invalidState = invalidState
        this.clearState = clearState
    }

    private fun performAction() {
        if (typedInput.isNotBlank()) {
            with(typedInput.trim()) {
                if ((regexPattern != null && checkForValid(
                        this,
                        regexPattern!!
                    )) || (predicate != null && predicate?.invoke(this) == true)
                ) {
                    listener?.onStateChange(STATE.VALID)
                    if (lastState == STATE.VALID) return
                    lastState = STATE.VALID
                    changeValues(validState = true)
                } else {
                    listener?.onStateChange(STATE.INVALID)
                    if (lastState == STATE.INVALID) return
                    lastState = STATE.INVALID
                    changeValues(invalidState = true)
                }
            }
        } else {
            clear()
        }
    }

    private fun clear() {
        listener?.onStateChange(STATE.CLEAR)
        if (lastState == STATE.CLEAR) return
        lastState = STATE.CLEAR
        changeValues(clearState = true)
    }

    // Constructors, view loading etc...
    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 4)
        if (typingState) {
            mergeDrawableStates(drawableState, STATE_TYPING)
        }
        if (validState) {
            mergeDrawableStates(drawableState, STATE_TICK)
        }
        if (invalidState) {
            mergeDrawableStates(drawableState, STATE_CROSS)
        }
        if (invalidState) {
            mergeDrawableStates(drawableState, STATE_CLEAR)
        }
        return drawableState
    }

    private var listener: StateChangeListener? = null
    fun addListener(listener: StateChangeListener?) {
        this.listener = listener
    }

}