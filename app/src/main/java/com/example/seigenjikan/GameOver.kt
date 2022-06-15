package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivityClearBinding
import com.example.seigenjikan.databinding.ActivityGameOverBinding

class GameOver : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_game_over)

        binding.TitleButton2.setOnClickListener{ onTitleButtonTapped() }

    }

    fun onTitleButtonTapped() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}