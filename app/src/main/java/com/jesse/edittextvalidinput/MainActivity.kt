package com.jesse.edittextvalidinput

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jesse.edittextvalidinput.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var textWatcher: TextWatcher

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup binding
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }

        // Initialize the textWatcher
        textWatcher =object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                binding.specialCodeTextInput.editText?.removeTextChangedListener(this)
                formatEditTextInput(p0)
                binding.specialCodeTextInput.editText?.addTextChangedListener(this)
            }
        }

        // Setup EditText
        binding.specialCodeTextInput.editText?.apply {
            addTextChangedListener(textWatcher)
            // This ensures that only numbers and "-" are allowed in the edit text
            val filter = InputFilter { source, _, _, _, _, _ ->
                val allowedChars = "0123456789-"
                for (char in source) {
                    if (!allowedChars.contains(char)) {
                        return@InputFilter ""
                    }
                }
                null
            }
            filters = arrayOf(filter)
        }
    }

    private fun formatEditTextInput(editable: Editable?) {
        if(editable != null ){
            if (editable.toString().length <= 8){
                // Remove existing dashes
                val stringWithoutDashes = editable.toString().replace("-", "")

                // Clear and append dashes based on desired format
                editable.clear()

                stringWithoutDashes.let {
                    editable.append(viewModel.formatSpecialCodeText(it))
                }
            } else{
                val oldText = editable.toString().substring(0, editable.toString().length - 1)
                editable.clear()
                editable.append(oldText)
            }
        }
    }
}