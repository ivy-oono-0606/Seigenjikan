package com.example.seigenjikan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var batle2: Battle2Fragment
    private lateinit var Command3: Command3Fragment
    private lateinit var Command5: Command5Fragment
    var minute:Long = 0
    var second:Long = 0
    var sikai = arrayOf(1, 2, 3)
    var sikaillen = sikai.size

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

    //フラグメントへ値を渡せます
    fun getItem(position: String,com:Int): Pair<Fragment?,Fragment?> {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", position)
        //現在未使用
        bundle.putInt("KEY_POSITION2", com)
        // Fragmentに値をセットする
        batle.setArguments(bundle)
        Command3.setArguments(bundle)
        if (sikaillen == 3){
            return Pair(batle,Command3)
        }else{
            return Pair(batle,Command5)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ここからタイマーフラグメント、バトルフラグメント表示
        timerF = TimerFragment()
        batle = BattleFragment()
        batle2 = Battle2Fragment()
        Command3 =  Command3Fragment()
        Command5 =  Command5Fragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.BatleFrame, batle)
            add(R.id.BatleFrame, Command3)
            addToBackStack("batle")
            //敵初期設定
            getItem("doragon",123)
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

        //ここからNPC表示
        fun test(View: View?){
            val intent = Intent(this, NpcSubActivity::class.java)
            startActivity(intent)
        }
        //ここまでNPC表示

        //ここからボタン判定
        var count : Int = 0
        fun hanntei(hand: Int){
            if (sikai[count] == hand) {
                binding.timerText.text = "正解"
                count++
                if (count==sikaillen){
                    count = 0
                    //敵設置
                    getItem("goburin",32112)
                    //再表示
                    supportFragmentManager.beginTransaction().apply{
                        replace(R.id.BatleFrame, timerF)
                        replace(R.id.BatleFrame, batle)
                        add(R.id.BatleFrame, Command5)
                        commit()
                    }
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
                replace(R.id.BatleFrame, batle2)
                commit()
            }
        }
        //メニューボタンここまで

        //赤ボタン
        binding.RedButton.setOnClickListener{
            hanntei(1)
        }
        //赤ボタンここまで

        //青ボタン
        binding.BlueButton.setOnClickListener{
            hanntei(2)
            //test(it)
        }

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(3)
        }
        //緑ボタンここまで
    }
}