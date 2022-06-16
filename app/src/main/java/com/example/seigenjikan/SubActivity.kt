package com.example.seigenjikan

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    //private lateinit var timer: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var NPC: NPCFragment

    //ここからタイマー定義
    inner class MyCountDownTimer(
            millisInFuture: Long,
            countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            binding.timerText.text = "0:00"
        }
    }
    //ここまでタイマー定義

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sikai = IntArray(3)
        sikai += 20
        //var sikaillen = sikai.size


        //ここからタイマーフラグメント、バトルフラグメント表示
        //timer = TimerFragment()
        batle = BattleFragment()
        NPC = NPCFragment()
        supportFragmentManager.beginTransaction().apply{
            //replace(R.id.TimerFrame, timer)
            replace(R.id.BattleFrame, batle)
            commit()
        }
        //ここまでタイマーフラグメント、バトルフラグメント表示

        //ここからタイマー操作
        binding.timerText.text = "3:00"
        var timer = MyCountDownTimer(3 * 60 * 1000, 100)
        binding.testbutton.setOnClickListener {
            timer.isRunning = when (timer.isRunning) {
                true -> {
                    //タイマーが動いているときに停止
                    timer.cancel()
                    false
                }
                false -> {
                    //タイマーが動いてないときに開始
                    timer.start()
                    true
                }
            }
        }
        //ここまでタイマー操作

        //ここからボタン判定
        for(i in sikai){
            binding.timerText.text = sikai[3].toString()
        }
        fun hanntei(hand: Int){
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
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.BattleFrame, NPC)
                commit()
            }
        }
        //青ボタンここまで

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(2)
        }
        //緑ボタンここまで


    }
}