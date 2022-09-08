package com.example.seigenjikan

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivitySelectSubBinding

class SelectSubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectSubBinding
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.MsButton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }
}