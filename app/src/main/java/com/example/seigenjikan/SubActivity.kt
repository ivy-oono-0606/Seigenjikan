package com.example.seigenjikan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.ActivitySubBinding
import java.util.*


class SubActivity : AppCompatActivity(),NPCFragment.NPCListener{
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var batle3: Battle3Fragment
    private lateinit var npc: NPCFragment
    private lateinit var command3: Command3Fragment
    private lateinit var command5: Command5Fragment
    var minute:Long = 0
    var second:Long = 0

    //現在配列最大数8（適宜変える）
    private val fragmentflag = arrayOf(0, 0, 1, 2, 2, 1, 1, 2);//呼び出すフラグメント0＝バトル１、１＝NPC、２＝バトル２
    private val enemy = arrayOf("suraimu", "goburin", "hourousya","doragon" ,"test","mobu","mobu","maou")//敵とNPCの画像
    private val sikai = arrayOf(arrayListOf(1, 2, 3), arrayListOf(3, 3, 2, 1, 2),arrayListOf(), arrayListOf(2, 1, 2, 1, 2), arrayListOf(3),arrayListOf(), arrayListOf(),arrayListOf(1, 3, 2,3,1))//正解コマンド
    private val back = arrayOf("mori","doukutu","tosi","tosi","jyounai","jyounai","jyounai","gyokuza")//背景画像
    private val text = arrayOf("",
            "",
            "俺の名前はグラン。この先にはワイバーンがいる。 もしあいつを倒そうと思っているなら俺がアドバイスしてやるよ。\n" +
            "奴の弱点は水の攻撃だ、これまでいろんなやつが挑んだが3回の攻撃じゃ倒れない。\n" +
            "火の攻撃は効くがあまりダメージは見込めない。弱点攻撃と組み合わせれば効くかもしれない。だが、この攻撃も１回だけじゃだめだ、２回は攻撃しないとな。\n" +
            "それと、草の攻撃は効かないから気を付けろよ。",
            "水→炎→水→　→",
            "赤と青を足し、緑を足し、蒼を引き、緑を引き、緋を2回掛け合わしたものでもなく青でもないものは ?",
            "ここから先は覚悟あるものだけが通ることを許された場所\n" +
                    "もし覚悟があるのならばこれから話す昔話を聞いていくがいい\n" +
                    "昔々魔王がこの世界を支配していた。そんな時一人の勇者が現れた。",
                    "そして、魔王と勇者との戦いが始まった。\n" +
                    "魔王は勇者に魔法を放ちそれを勇者が水で受け流し、すかさず勇者が反撃する。\n" +
                    "自然の力で魔王の両足を縛り魔王の態勢を崩した後、炎を纏った剣で魔王の片腕を切り落とす。\n" +
                    "最後に3つの力を組み合わせた攻撃により魔王を打ち取った",
            "1first → 4 →　→　→")//表示テキスト
    private var sikaiX : Int = 0
    private var sikaiY :Int = 0
    private var timer = MyCountDownTimer((1 * 60) * 1000, 100)
    var Tsikai = arrayListOf<Int>()

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
            gameover()
        }
    }
    //ここまでタイマー定義

    //ここから画面遷移
    private fun gameover(){
        val intent = Intent(this, GameOver::class.java)
        startActivity(intent)
    }
    private fun clear(){
        val intent = Intent(this, Clear::class.java)
        startActivity(intent)
    }
    //ここまで画面遷移

    //バトルフラグメントへ値を渡せます
    private fun getItem(position: String, com: ArrayList<Int>, Siz: Int): Pair<Fragment?, Fragment?> {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", position)
        //コマンド送信用
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

    //フラグメントへ値を渡せます(オーバーロード:バトルフラグメント２用)
    private fun getItem(position: String, text: String): Fragment? {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", position)
        //テキスト送信用
        bundle.putString("KEY_POSITION2", text)
        // Fragmentに値をセットする
        batle3.arguments = bundle
        return batle3
    }

    //フラグメントへ値を渡せます(オーバーロード:NPCフラグメント用)
     fun getItem(charaimage: String, backimage: String, text: String): Fragment? {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putString("KEY_POSITION", charaimage)
        //テキスト送信用
        bundle.putString("KEY_POSITION2", backimage)
        //テキスト送信用
        bundle.putString("KEY_POSITION3", text)
        // Fragmentに値をセットする
        npc.arguments = bundle
        return npc
    }

    //バトルフラグメント表示メソッド
    private fun batlefragmentdisplay(){
        supportFragmentManager.beginTransaction().apply{
            if (fragmentflag[sikaiY] == 0){//バトル1の場合
                remove(batle)//更新のための削除
                batle = BattleFragment()//再定義
                replace(R.id.BatleFrame, batle)//表示処理
                if (sikai[sikaiY].size == 3){
                    remove(command3)
                    command3 =  Command3Fragment()
                    add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
                }else if (sikai[sikaiY].size == 5){
                    remove(command5)
                    command5 =  Command5Fragment()
                    add(R.id.BatleFrame, command5)
                }
                getItem(enemy[sikaiY], sikai[sikaiY], sikai[sikaiY].size)
            }else if (fragmentflag[sikaiY] == 2){//バトル2の場合
                remove(batle3)
                batle3 = Battle3Fragment()
                replace(R.id.BatleFrame, batle3)
                getItem(enemy[sikaiY], text[sikaiY])
            }
            addToBackStack(null);
            commit()
        }
        val packageName = packageName //packageName取得
        val imageId = resources.getIdentifier(back[sikaiY], "drawable", packageName) //リソースIDのを取得
        binding.haikei.setBackgroundResource(imageId) //画像のリソースIDで画像表示
    }

    //NPCフラグメント表示メソッド
    private fun test(){
         sikai[sikaiY][sikaiX] = sikai[sikaiY][sikaiX] *10
        supportFragmentManager.beginTransaction().apply {
            if (sikai[sikaiY].size == 3) {
                remove(command3)
                command3 = Command3Fragment()
                add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
            } else if (sikai[sikaiY].size == 5) {
                remove(command5)
                command5 = Command5Fragment()
                add(R.id.BatleFrame, command5)
            }
            getItem(enemy[sikaiY], sikai[sikaiY], sikai[sikaiY].size)
            commit()
        }
    }

    private fun test2(){
        var i = 0
        if (sikaiX != 0){
            while (i < sikaiX) {
                sikai[sikaiY][i] = sikai[sikaiY][i] /10
                i++
            }
        }

        supportFragmentManager.beginTransaction().apply {
            if (sikai[sikaiY].size == 3) {
                remove(command3)
                command3 = Command3Fragment()
                add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
            } else if (sikai[sikaiY].size == 5) {
                remove(command5)
                command5 = Command5Fragment()
                add(R.id.BatleFrame, command5)
            }
            getItem(enemy[sikaiY], sikai[sikaiY], sikai[sikaiY].size)
            commit()
        }
    }

    private fun test3(){
        //sikai[sikaiY][sikaiX] = sikai[sikaiY][sikaiX] *10
        Tsikai.add(sikai[sikaiY][sikaiX])
        supportFragmentManager.beginTransaction().apply {
            if (sikai[sikaiY].size == 3) {
                remove(command3)
                command3 = Command3Fragment()
                add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
            } else if (sikai[sikaiY].size == 5) {
                remove(command5)
                command5 = Command5Fragment()
                add(R.id.BatleFrame, command5)
            }
            getItem(enemy[sikaiY], Tsikai, sikai[sikaiY].size)
            commit()
        }
    }

    private fun test4(){
        supportFragmentManager.beginTransaction().apply {
            if (sikai[sikaiY].size == 3) {
                remove(command3)
            } else if (sikai[sikaiY].size == 5) {
                remove(command5)
            }
            commit()
        }
    }

    //NPCフラグメント表示メソッド
    private fun npcfragmentdisplay(){
        timer.cancel()
        timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
        binding.RedButton.visibility = View.INVISIBLE;
        binding.BlueButton.visibility = View.INVISIBLE;
        binding.GreenButton.visibility = View.INVISIBLE;
        supportFragmentManager.beginTransaction().apply{
            remove(npc)//更新のための削除
            npc = NPCFragment()
            replace(R.id.haikei, npc)
            getItem(enemy[sikaiY], back[sikaiY],text[sikaiY])
            commit()
        }
        binding.titleButton.visibility = View.INVISIBLE;
        sikaiY++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ここからタイマーフラグメント、バトルフラグメント表示
        npc = NPCFragment()
        timerF = TimerFragment()
        batle = BattleFragment()
        batle3 = Battle3Fragment()
        command3 =  Command3Fragment()
        command5 =  Command5Fragment()
        batlefragmentdisplay()
        //ここまでタイマーフラグメント、バトルフラグメント表示

        //ここからタイマー操作
        binding.timerText.text = "3:00"
        timer = MyCountDownTimer((3 * 60) * 1000, 100)
        timer.start()
        //binding.testbutton.visibility = View.INVISIBLE;
        binding.testbutton.setOnClickListener {
            binding.RedButton.visibility = View.INVISIBLE;
            /*timer.cancel()
            timer = MyCountDownTimer((3 * 60) * 1000, 100)*/
            /*timer.isRunning = when (timer.isRunning) {
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
            }*/
        }
        //ここまでタイマー操作

        //ここからボタン判定
        fun hanntei(hand: Int){
            //判定式と正解に１０をかけることで表示が増やせる（例・・・sikai = 10 = hand*10）
            if (sikai[sikaiY][sikaiX] == hand) {
                binding.timerText.text = "正解"
                if (fragmentflag[sikaiY] == 0){
                    test()
                }else{
                    test3()
                }
                sikaiX++
                if (sikaiX==sikai[sikaiY].size){
                    Tsikai = arrayListOf<Int>()
                    sikaiX = 0
                    sikaiY++
                    if (sikaiY >= sikai.size){
                        sikaiY = 0
                        timer.cancel()
                        clear()
                    }else if (fragmentflag[sikaiY] == 1){
                        //NPCフラグメント表示
                        npcfragmentdisplay()
                        batlefragmentdisplay()
                    }else if (fragmentflag[sikaiY] != 1){
                        //バトルフラグメント表示
                        batlefragmentdisplay()
                    }
                }
            }else {
                //binding.timerText.text = "不正解"
                Tsikai = arrayListOf<Int>()
                if (fragmentflag[sikaiY] == 0){
                    test2()
                }else{
                    test4()
                }
                sikaiX = 0
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
        }

        //緑ボタン
        binding.GreenButton.setOnClickListener{
            hanntei(3)
        }
        //緑ボタンここまで
    }

    override fun onClickNext() {
        if (fragmentflag[sikaiY] == 1){
            //NPCフラグメント表示
            npcfragmentdisplay()
        }else if (fragmentflag[sikaiY] != 1){
            timer.start()
            binding.titleButton.visibility = View.VISIBLE;
            binding.RedButton.visibility = View.VISIBLE;
            binding.BlueButton.visibility = View.VISIBLE;
            binding.GreenButton.visibility = View.VISIBLE;
            //バトルフラグメント表示
            batlefragmentdisplay()
        }
    }

    /*override fun onResume() {
        super.onResume()
        if (sikaiY != 0){
            fragmentdisplay()
        }
    }*/

    override fun onBackPressed() {}
}