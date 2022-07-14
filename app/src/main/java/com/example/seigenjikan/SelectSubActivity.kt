package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.seigenjikan.databinding.ActivitySelectSubBinding

class SelectSubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectSubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.MsButton.setOnClickListener{onstartbuttontapped(it)}
        binding.DjButton.setOnClickListener{onDjButtontapped(it)}
        binding.DragonButton.setOnClickListener{onDragonButtontapped(it)}
        binding.DevilButton.setOnClickListener{onDevilButtontapped(it)}
    }

    fun onstartbuttontapped(View: View?){
        val intent = Intent(getApplication(), SubActivity::class.java)
        startActivity(intent)
    }

    fun onDjButtontapped(View: View?){
        val intent = Intent(getApplication(), SubActivity::class.java)
        startActivity(intent)
    }

    fun onDragonButtontapped(View: View?){
        val intent = Intent(getApplication(), SubActivity::class.java)
        startActivity(intent)
    }

    fun onDevilButtontapped(View: View?){
        val intent = Intent(getApplication(), SubActivity::class.java)
        startActivity(intent)
    }

}