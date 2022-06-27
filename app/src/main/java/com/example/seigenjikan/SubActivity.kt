package com.example.seigenjikan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.ActivitySubBinding
import java.util.*


class SubActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var command3: Command3Fragment
    private lateinit var command5: Command5Fragment
    var minute:Long = 0
    var second:Long = 0

    //ここからNPC表示
    fun test(){
        val intent = Intent(this, NpcSubActivity::class.java)
        startActivity(intent)
    }
    fun test2(){
        val intent = Intent(this, GameOver::class.java)
        startActivity(intent)
    }
    fun test3(){
        val intent = Intent(this, Clear::class.java)
        startActivity(intent)
    }
    //ここまでNPC表示

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
            test2()
        }
    }
    //ここまでタイマー定義

    //フラグメントへ値を渡せます
    fun getItem(position: String, com: ArrayList<Int>, Siz: Int): Pair<Fragment?, Fragment?> {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", position)
        //現在未使用
        bundle.putIntegerArrayList("KEY_POSITION2", com)
        // Fragmentに値をセットする
        batle.arguments = bundle
        if (Siz == 3){
            command3.arguments = bundle
            return Pair(batle, command3)
        }else{
            command5.arguments = bundle
            return Pair(batle, command5)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sikai = arrayListOf(1,2,3)
        var enemy = arrayOf("suraimu", "goburin", "doragon", "doragon")
        var sikai = arrayOf(arrayListOf(1, 2, 3), arrayListOf(3, 3, 2, 1, 2), arrayListOf(2, 3, 1, 1, 3), arrayListOf(3, 2, 2, 2, 1))
        var back = arrayOf("mori","doukutu","tosi","tosi")
        var sikaiY :Int = 0


        //バトルフラグメント表示メソッド
        fun Fragmentdisplay(){
            supportFragmentManager.beginTransaction().apply{
                remove(batle)
                batle = BattleFragment()
                replace(R.id.BatleFrame, batle)
                if (sikai[sikaiY].size == 3){
                    remove(command3)
                    command3 =  Command3Fragment()
                    add(R.id.BatleFrame, command3)
                }else if (sikai[sikaiY].size == 5){
                    remove(command5)
                    command5 =  Command5Fragment()
                    add(R.id.BatleFrame, command5)
                }
                getItem(enemy[sikaiY], sikai[sikaiY], sikai[sikaiY].size)
                commit()
            }
        }

        //ここからタイマーフラグメント、バトルフラグメント表示
        timerF = TimerFragment()
        batle = BattleFragment()
        command3 =  Command3Fragment()
        command5 =  Command5Fragment()
        Fragmentdisplay()
        //ここまでタイマーフラグメント、バトルフラグメント表示

        //ここからタイマー操作
        binding.timerText.text = "3:00"
        var timer = MyCountDownTimer((3 * 60) * 1000, 100)
        timer.start()
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
        var sikaiX : Int = 0
        fun hanntei(hand: Int){
            //判定式と正解に１０をかけることで表示が増やせる（例・・・sikai = 1 = 1*10）
            if (sikai[sikaiY][sikaiX] == hand) {
                binding.timerText.text = "正解"
                sikaiX++
                if (sikaiX==sikai[sikaiY].size){
                    sikaiX = 0
                    sikaiY++
                    if (sikaiY >= sikai.size){
                        sikaiY = 0
                        test3()
                    }else{
                        //再表示
                        Fragmentdisplay()
                        val packageName = packageName //packageName取得
                        val imageId = resources.getIdentifier(back[sikaiY], "drawable", packageName) //リソースIDのを取得
                        binding.haikei.setBackgroundResource(imageId) //画像のリソースIDで画像表示
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
                replace(R.id.BatleFrame, timerF)
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
            //test()
        }

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(3)
        }
        //緑ボタンここまで
    }
}