package com.example.seigenjikan

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.ActivitySelectSubBinding

class SelectSubActivity : AppCompatActivity() ,configFragment.configListener,scoreFragment.scoreListener {
    private lateinit var binding: ActivitySelectSubBinding
    private lateinit var config: configFragment
    private lateinit var Score: scoreFragment

    private fun getItem(SE: Boolean): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        val bundle = Bundle()
        bundle.putBoolean("KEY_POSITION", SE)//SE設定用送信用
        bundle.putInt("KEY_POSITION2", 1)//展開アクティビティ送信用
        config.arguments = bundle
        return config
    }

    private fun getItem(str1:String,str2:String,str3:String,str4:String): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", str1)//テキスト１送信用
        bundle.putString("KEY_POSITION2", str2)//テキスト２送信用
        bundle.putString("KEY_POSITION3", str3)//テキスト３送信用
        bundle.putString("KEY_POSITION4", str4)//テキスト４送信用
        Score.arguments = bundle
        return Score
    }

    override fun backconfig() {//configフラグメントから戻る受け取り
        binding.titleButton2.isClickable=true
        binding.Scorebutton.isClickable=true
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

    override fun retire() {//configフラグメントからリタイア(ボタンを表示しないため処理なし)
    }

    override fun textchange(int:Int) {//Scoreフラグメントから表示選択
        var str1:String = ""
        var str2:String = ""
        var str3:String = ""
        var str4:String = ""
        if(int == 1){
            str1 = "ストーリー"
            if(1 == 1){
                str2 = "クリア済み"
            }
            str3 = ""
            str4 = ""
        }else if (int == 2){
            str1 = "ダンジョン"
            if(1 == 1){
                str2 = "クリア済み"
            }
            str3 = ""
            str4 = ""
        }else if (int == 3){
            str1 = "竜の谷"
            if(1 == 1){
                str2 = "クリア済み"
            }
            str3 = ""
            str4 = ""
        }else if (int == 4){
            str1 = "悪魔の住処"
            if(1 == 1){
                str2 = "クリア済み"
            }
            str3 = ""
            str4 = ""
        }
        supportFragmentManager.beginTransaction().apply {
            Score = scoreFragment()
            getItem(str1,str2,str3,str4)
            add(R.id.config, Score)
            commit()
        }
    }

    fun Buttonoff(){
        //ボタン無力化
        binding.titleButton2.isClickable=false
        binding.Scorebutton.isClickable=false
        binding.MsButton.isClickable=false
        binding.DjButton.isClickable = false
        binding.DragonButton.isClickable = false
        binding.DevilButton.isClickable = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み

        binding.MsButton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",1)
            startActivity(intent)
        }

        binding.DjButton.setOnClickListener{
//            val intent = Intent(this, TestSubActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",2)
            startActivity(intent)
        }

        binding.DragonButton.setOnClickListener{
            /*val intent = Intent(this, TestSubActivity::class.java)
            startActivity(intent)*/
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",3)
            startActivity(intent)
        }

        binding.DevilButton.setOnClickListener{
//            val intent = Intent(this, TestSubActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",4)
            startActivity(intent)
        }

        binding.titleButton2.setOnClickListener{//ここからメニューボタン
            Buttonoff()
            supportFragmentManager.beginTransaction().apply {
                config = configFragment()
                getItem(pref.getBoolean("SE",false))
                add(R.id.config, config)
                commit()
            }
        }

        binding.Scorebutton.setOnClickListener{//ここからスコアボタン
            Buttonoff()
            textchange(1)
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