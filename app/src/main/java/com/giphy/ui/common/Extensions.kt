package com.giphy.ui.common

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat


fun EditText.hideKeyboard() {
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}