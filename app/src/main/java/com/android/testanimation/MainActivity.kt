package com.android.testanimation

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.android.testanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.validAnimView.setUpWithEditText(binding.inputField1, Patterns.EMAIL_ADDRESS.toString())
    }

}
