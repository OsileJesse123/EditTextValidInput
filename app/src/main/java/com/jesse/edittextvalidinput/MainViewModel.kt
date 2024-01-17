package com.jesse.edittextvalidinput

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    val firstName = MutableLiveData<String>()

    fun formatSpecialCodeText(input: String): StringBuilder {
        // Initialize a StringBuilder to store the formatted text
        val formattedText = StringBuilder()

        // Iterate through each character in the input string
        for (i in input.indices) {
            // Append the current character to the StringBuilder
            formattedText.append(input[i])

            // Check if the current index is even and not the last character in the input string
            if ((i + 1) % 2 == 0 && i < input.length - 1) {
                // If true, append a hyphen "-" to the StringBuilder
                formattedText.append("-")
            }
        }

        // Return the final formatted text as a StringBuilder
        return formattedText
    }
}