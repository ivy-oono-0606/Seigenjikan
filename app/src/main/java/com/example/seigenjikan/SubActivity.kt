package com.example.seigenjikan

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivitySubBinding
import com.example.seigenjikan.databinding.FragmentTimerBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    private lateinit var binding2: FragmentTimerBinding
    private lateinit var timer: TimerFragment
    private lateinit var batle: BattleFragment

    inner class MyCountDownTimer(
            millisInFuture: Long,
            countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding2.timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            binding2.timerText.text = "0:00"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        binding2 = FragmentTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ここからタイマーフラグメント、バトルフラグメント表示
        timer = TimerFragment()
        batle = BattleFragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.TimerFrame, timer)
            replace(R.id.BattleFrame,batle)
            commit()
        }
        //ここまでタイマーフラグメント、バトルフラグメント表示

        //ここからタイマー処理
        binding2.timerText.text = "3:00"
        var timer = MyCountDownTimer(3 * 60 * 1000, 100)
        timer.start()
        timer.isRunning = true
        //ここまでタイマー処理

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
        /*binding.RedButton.setOnClickListener{
            hanntei(0)
        }*/
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