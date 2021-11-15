package com.spot.acronyms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.spot.acronyms.R
import com.spot.acronyms.adapters.orEmpty

fun EditText.onDone(callback: () -> Unit = {}) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
            hideSoftKeyboard()
            return@setOnEditorActionListener true
        }
        false
    }
}

fun View.hideSoftKeyboard() {
    val inputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.setClearIcon() {
    val drawableEnd = ContextCompat.getDrawable(context, R.drawable.ic_close)
    doOnTextChanged { text, _, _, _ ->
        setCompoundDrawablesWithIntrinsicBounds(
            compoundDrawables[0],
            compoundDrawables[1],
            if (text.orEmpty().isNotBlank()) drawableEnd else null,
            compoundDrawables[3],

        )
    }
    setOnClickListener {
        setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN && compoundDrawables[2] != null && motionEvent.x >= (right - (paddingLeft * 2) - paddingRight - compoundDrawables[2].bounds.width())) {
                if (text.isNotEmpty()) {
                    setText("")
                }
            }
            return@setOnTouchListener false
        }
    }
}
