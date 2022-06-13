package com.example.seigenjikan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    private lateinit var timer: TimerFragment
    private lateinit var batle: BattleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ここからタイマー表示
        timer = TimerFragment()
        batle = BattleFragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.TimerFrame, timer)
            replace(R.id.BattleFrame,batle)
            commit()
        }
        //ここまでタイマー表示

        //ここからボタン判定
        fun hanntei(hand:Int){
            var test : Int = 0
            if (test == hand) {
                //正解
            }else {
                //不正解
            }
        }
        //ここまでボタン判定

        //赤ボタン
        binding.RedButton.setOnClickListener{
            hanntei(0)
        }
        //赤ボタンここまで

        //青ボタン
        binding.BlueButton.setOnClickListener{
            hanntei(1)
        }
        //青ボタンここまで

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(2)
        }
        //緑ボタンここまで


    }
}