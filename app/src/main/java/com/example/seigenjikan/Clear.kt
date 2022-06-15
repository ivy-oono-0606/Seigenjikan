package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivityClearBinding

class Clear : AppCompatActivity() {
    private lateinit var binding: ActivityClearBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClearBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_clear)

        binding.TitleButton1.setOnClickListener{ onTitleButtonTapped() }

    }

    fun onTitleButtonTapped() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}