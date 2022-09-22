package com.example.seigenjikan

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.seigenjikan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mp = MediaPlayer.create(this,R.raw.fabgm);
        mp.setLooping(true);//    ループ設定
        //mp.setVolume(float ,float );
        defBGMstartLoop(this)

        binding.startbutton.setOnClickListener{onstartbuttontapped(it)}
        binding.button.setOnClickListener{
            mp.stop()
            BGMstop()
        }
    }

    fun onstartbuttontapped(View: View?){
        val intent = Intent(this, SelectSubActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        BGMRestart()
    }

    override fun onPause() {
        super.onPause()
        BGMpause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //mp.stop()
    }

    override fun onBackPressed() {}
}