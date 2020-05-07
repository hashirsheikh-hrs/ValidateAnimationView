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
        //2nd manager by code
//        binding.validAnimView.setUpWithEditText(binding.inputField1, Patterns.EMAIL_ADDRESS.toString())

        //3rd manage by your own
//        binding.validAnimView.typingState = true //when show a typing animation
//        binding.validAnimView.validState = true //when show validation animation
//        binding.validAnimView.invalidState = true //when show invalid animation
//        binding.validAnimView.clearState = true //when want to clear the animation
    }

}
