package com.example.seigenjikan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.seigenjikan.databinding.ActivitySubBinding

class MainViewModel: ViewModel(){
    var enemyimage:String = ""
}

class SubActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    var minute:Long = 0
    var second:Long = 0


    //ここからタイマー定義
    inner class MyCountDownTimer(
            millisInFuture: Long,
            countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        var isRunning = false
        override fun onTick(millisUntilFinished: Long) {
            minute = millisUntilFinished / 1000L / 60L
            second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)
        }
        override fun onFinish() {
            binding.timerText.text = "0:00"
        }
    }
    //ここまでタイマー定義

    fun getItem(position: String): Fragment? {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", position)
        // Fragmentに値をセットする
        timerF.setArguments(bundle)

        return timerF
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //var sikai = IntArray(3,{ 10 * (it + 1) })
        var sikai = arrayOf(0, 1, 2)
        var sikaillen = sikai.size

        //ここからタイマーフラグメント、バトルフラグメント表示
        timerF = TimerFragment()
        batle = BattleFragment()
        supportFragmentManager.beginTransaction().apply{
            //replace(R.id.TimerFrame, timer)
            replace(R.id.BattleFrame, batle)
            addToBackStack("batle")
            commit()
        }
        //ここまでタイマーフラグメント、バトルフラグメント表示

        //ここからタイマー操作
        binding.timerText.text = "3:00"
        var timer = MyCountDownTimer((3 * 60) * 1000, 100)
        binding.testbutton.setOnClickListener {
            timer.cancel()
            timer = MyCountDownTimer((3 * 60) * 1000, 100)
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
            binding.timerText.text = sikai[1].toString()
        }
        var count : Int = 0
        fun hanntei(hand: Int){
            if (sikai[count] == hand) {
                binding.timerText.text = "正解"
                count++
                if (count==sikaillen){
                    count = 0
                }
            }else {
                //binding.timerText.text = "不正解"
                timer.cancel()
                timer = MyCountDownTimer((minute * 60 + second - 10) * 1000, 100)
                timer.start()
            }
        }
        //ここまでボタン判定

        //メニューボタン
        binding.titleButton.setOnClickListener{
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.BattleFrame, batle)
                commit()
            }
        }
        //メニューボタンここまで

        //赤ボタン
        binding.RedButton.setOnClickListener{
            hanntei(0)
        }
        //赤ボタンここまで
        fun test(View: View?){
            val intent = Intent(this, NpcSubActivity::class.java)
            startActivity(intent)
        }
        //青ボタン
        binding.BlueButton.setOnClickListener{
            hanntei(1)
            /*getItem("")
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.BattleFrame, timerF)
                commit()
            }*/
            test(it)
        }

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(2)
        }
        //緑ボタンここまで
    }
}