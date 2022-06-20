package com.example.seigenjikan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivityNpcSubBinding

class NpcSubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNpcSubBinding
    private lateinit var NPC: NPCFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNpcSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NPC = NPCFragment()

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.HaikeiFrame, NPC)
            commit()
        }
    }
}