package com.example.seigenjikan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seigenjikan.databinding.ActivityNpcSubBinding

class NpcSubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNpcSubBinding
    private lateinit var NPC: NPCFragment
    private lateinit var MOB: MOBFragment
    var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNpcSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NPC = NPCFragment()
        MOB = MOBFragment()

        supportFragmentManager.beginTransaction().apply{
            if (count == 0){
                replace(R.id.HaikeiFrame, NPC)
                commit()
                count = count + 1
            }

            /*if (count == 1){
                replace(R.id.HaikeiFrame, MOB)
                commit()
            }*/

        }
    }
}