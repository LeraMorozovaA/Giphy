package com.giphy.ui.common

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun EditText.hideKeyboard() {
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun MaterialAlertDialogBuilder.showAlert(title: String, message: String, textButton: String) {
    setTitle(title)
    setMessage(message)
    setPositiveButton(textButton) { dialog, _ -> dialog.dismiss() }
    show()
}