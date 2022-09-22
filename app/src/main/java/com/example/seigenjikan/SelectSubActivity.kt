package com.example.seigenjikan

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.ActivitySelectSubBinding

class SelectSubActivity : AppCompatActivity() ,configFragment.configListener{
    private lateinit var binding: ActivitySelectSubBinding
    private lateinit var config: configFragment

    private fun getItem(SE: Boolean): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        val bundle = Bundle()
        bundle.putBoolean("KEY_POSITION", SE)//SE設定用送信用
        config.arguments = bundle
        return config
    }

    override fun backconfig() {//configフラグメントから戻る受け取り
        binding.MsButton.isClickable= true
        binding.DjButton.isClickable = true
        binding.DragonButton.isClickable = true
        binding.DevilButton.isClickable = true
    }

    override fun SEconfig(bool:Boolean) {//configフラグメントからSE
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        pref.edit {
            putBoolean("SE",bool)
        }
    }

    override fun retire() {//configフラグメントからリタイア

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み

        binding.MsButton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }

        binding.DjButton.setOnClickListener{
//            val intent = Intent(this, TestSubActivity::class.java)
//            startActivity(intent)
        }

        binding.DragonButton.setOnClickListener{
            val intent = Intent(this, TestSubActivity::class.java)
            startActivity(intent)
        }

        binding.DevilButton.setOnClickListener{
//            val intent = Intent(this, TestSubActivity::class.java)
//            startActivity(intent)
        }

        binding.titleButton2.setOnClickListener{//ここからメニューボタン現在機能未定
            //ボタン無力化
            binding.MsButton.isClickable=false
            binding.DjButton.isClickable = false
            binding.DragonButton.isClickable = false
            binding.DevilButton.isClickable = false
            supportFragmentManager.beginTransaction().apply {
                config = configFragment()
                getItem(pref.getBoolean("SE",false))
                replace(R.id.config, config)
                commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        defBGMstartLoop(this)
    }

    override fun onPause() {
        super.onPause()
        BGMpause()
    }

}