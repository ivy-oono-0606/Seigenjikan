package com.example.seigenjikan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    private lateinit var timer: TimerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer = TimerFragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.TimerFrame, timer)
            commit()
        }
    }
}