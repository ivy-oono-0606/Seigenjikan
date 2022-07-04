package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.seigenjikan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startbutton.setOnClickListener{onstartbuttontapped(it)}
    }

    fun onstartbuttontapped(View: View?){
        val intent = Intent(this, SubActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {}
}