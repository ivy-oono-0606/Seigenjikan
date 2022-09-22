package com.example.seigenjikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivityGameOverBinding

class GameOver : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GMOVBGMstartLoop(this)

        binding.TitleButton2.setOnClickListener{ onTitleButtonTapped() }

    }

    fun onTitleButtonTapped() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {}
}