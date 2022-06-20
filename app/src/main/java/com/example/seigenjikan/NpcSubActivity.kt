package com.example.seigenjikan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivitySubBinding

class NpcSubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding
    private lateinit var NPC: NPCFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NPC = NPCFragment()

        supportFragmentManager.beginTransaction().apply{
            //replace(R.id.TimerFrame, timer)
            replace(R.id.HaikeiFrame, NPC)
            commit()
        }
    }
}