package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.ActivitySelectSub2Binding

class SelectSubActivity2 : AppCompatActivity() ,configFragment.configListener,scoreFragment.scoreListener{
    private lateinit var binding: ActivitySelectSub2Binding
    private lateinit var config: configFragment
    private lateinit var Score: scoreFragment

    private fun getItem(SE: Boolean,AE: Boolean): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        val bundle = Bundle()
        bundle.putBoolean("KEY_POSITION", SE)//SE設定用送信用
        bundle.putInt("KEY_POSITION2", 1)//展開アクティビティ送信用
        bundle.putBoolean("KEY_POSITION3", AE)//SE設定用送信用
        config.arguments = bundle
        return config
    }

    private fun getItem(str1:String,str2:String,str3:String,str4:String): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", str1)//テキスト１送信用
        bundle.putString("KEY_POSITION2", str2)//テキスト２送信用
        bundle.putString("KEY_POSITION3", str3)//テキスト３送信用
        bundle.putString("KEY_POSITION4", str4)//テキスト４送信用
        bundle.putBoolean("KEY_POSITION5", true)//テキスト４送信用
        Score.arguments = bundle
        return Score
    }

    override fun backconfig() {//configフラグメントから戻る受け取り
        binding.titleButton4.isClickable=true
        binding.scorebutton.isClickable=true
        binding.suraimubutton.isClickable=true
        binding.randombutton.isClickable=true
        binding.chngeimageButton2.isClickable=true
    }

    override fun SEconfig(bool:Boolean) {//configフラグメントからSE
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        pref.edit {
            putBoolean("SE",bool)
        }
    }

    override fun AEconfig(bool:Boolean) {//configフラグメントからSE
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        pref.edit {
            putBoolean("AE",bool)
        }
    }

    override fun retire() {//configフラグメントからリタイア(ボタンを表示しないため処理なし)
    }

    override fun textchange(int:Int) {//Scoreフラグメントから表示選択
        var str1:String = ""
        var str2:String = ""
        var str3:String = ""
        var str4:String = ""
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み

        if (int == 1){
            str1 = "スライムの森"
            if(pref.getBoolean("S5",false)){
                str2 = "クリア済み"
                str3 = "最大討伐数:"+pref.getInt("S5kill",0).toString()
            }else{
                str2 = "未クリア"
            }
        }else if (int == 2){
            str1 = "Rダンジョン"
            if(pref.getBoolean("S6",false)){
                str2 = "クリア済み"
                str3 = "最速クリアタイム\n残りタイム:"+(pref.getLong("S6time",0)/60).toString()+":"+(pref.getLong("S6time",0)%60).toString()
            }else{
                str2 = "未クリア"
            }
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
        binding.titleButton4.isClickable=false
        binding.scorebutton.isClickable=false
        binding.suraimubutton.isClickable=false
        binding.randombutton.isClickable=false
        binding.chngeimageButton2.isClickable=false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSub2Binding.inflate(layoutInflater)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み

        binding.suraimubutton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",5)
            startActivity(intent)
        }

        binding.randombutton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("mode",6)
            startActivity(intent)
        }

        binding.titleButton4.setOnClickListener{//ここからメニューボタン
            Buttonoff()
            supportFragmentManager.beginTransaction().apply {
                config = configFragment()
                getItem(pref.getBoolean("SE",true),pref.getBoolean("AE",true))
                add(R.id.config, config)
                commit()
            }
        }

        binding.scorebutton.setOnClickListener{//ここからスコアボタン
            Buttonoff()
            textchange(1)
        }

        binding.chngeimageButton2.setOnClickListener{//ここからステージセレクト２ボタン
            val intent = Intent(this, SelectSubActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
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