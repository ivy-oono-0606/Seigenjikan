package com.example.seigenjikan

import android.content.Intent
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.ActivitySubBinding
import java.util.*

class SubActivity : AppCompatActivity(),NPCFragment.NPCListener ,MoveFragment.MoveListener,TreasureChestFragment.TreasureChestListener{
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var batle3: Battle3Fragment
    private lateinit var npc: NPCFragment
    private lateinit var command3: Command3Fragment
    private lateinit var command5: Command5Fragment
    private lateinit var moveFragment: MoveFragment
    private lateinit var treasureChestFragment: TreasureChestFragment
    var minute:Long = 0
    var second:Long = 0

    //現在配列最大数9（適宜変える）
    private var GameFlagBranch = listOf<GameFlagBranch>()
    //呼び出すフラグメント0＝バトル１、１＝NPC、２＝バトル２ 3＝ダンジョン突入 4＝ダンジョン脱出 5＝方向　6＝宝箱
    /*private val fragmentflag = arrayOf(3, 0, 0, 1, 2, 2, 1, 1, 2);
    private val enemy = arrayOf("", "suraimu", "goburin", "hourousya", "doragon", "test", "mobu", "mobu", "maou")//敵とNPCの画像
    private val sikai = arrayOf(arrayListOf(), arrayListOf(1, 2, 3), arrayListOf(3, 3, 2, 1, 2), arrayListOf(), arrayListOf(2, 1, 2, 1, 2),
            arrayListOf(3), arrayListOf(), arrayListOf(), arrayListOf(1, 3, 2, 3, 1))//原則正解コマンドだが、進行方向処理の場合は配列の移動知
    private val back = arrayOf("", "mori", "doukutu", "tosi", "tosi", "jyounai", "jyounai", "jyounai", "gyokuza")//背景画像
    private val text = arrayOf("", "",
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
            "1first → 4 →　→　→")//表示テキスト*/
    private var FlagX : Int = 0
    private var FlagY : Int = 0
    private var subFlagX : Int = 0
    private var subFlagY : Int = 0
    private var subGameFlagBranch = listOf<GameFlagBranch>()
    private var timer = MyCountDownTimer((1 * 60) * 1000, 100)
    var viewanswer = arrayListOf<Int>()//正解したときの表示用配列
    //private val TT = arrayOf(arrayOf(1, "敵", arrayListOf(2, 2, 2), "背景", "テキスト"))//新コマンド

    inner class MyCountDownTimer(//ここからタイマー定義----------------------------------------------
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
    }//ここまでタイマー定義--------------------------------------------------------------------------

    private fun gameover(){//ここから画面遷移--------------------------------------------------------
        val intent = Intent(this, GameOver::class.java)
        startActivity(intent)
    }
    private fun clear(){
        val intent = Intent(this, Clear::class.java)
        startActivity(intent)
    }//ここまで画面遷移------------------------------------------------------------------------------

    private fun getItem(enemyimage: String, com: ArrayList<Int>, Siz: Int): Pair<Fragment?, Fragment?> {//バトルフラグメントへ値を渡せます（バトルフラグメント用）
        val bundle = Bundle()// Bundle（オブジェクトの入れ物）のインスタンスを作成する
        bundle.putString("KEY_POSITION", enemyimage)//敵画像送信用(bundleの後のPuによt~~は送る変数によって変える)
        bundle.putIntegerArrayList("KEY_POSITION2", com)//コマンド送信用
        batle.arguments = bundle// Fragmentに値をセットする
        if (Siz == 3){
            command3.arguments = bundle
            return Pair(batle, command3)
        }else{
            command5.arguments = bundle
            return Pair(batle, command5)
        }
    }

    private fun getItem(enemyimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(オーバーロード:バトルフラグメント２用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", enemyimage)//敵画像送信用
        bundle.putString("KEY_POSITION2", text)//テキスト送信用
        batle3.arguments = bundle
        return batle3
    }

    private fun getItem(charaimage: String, backimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(オーバーロード:NPCフラグメント用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", charaimage)//キャラ画像送信用
        bundle.putString("KEY_POSITION2", backimage)//背景画像送信用
        bundle.putString("KEY_POSITION3", text)//テキスト送信用
        npc.arguments = bundle
        return npc
    }

    private fun treasuregetItem(treasurimage: String ,backimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(オーバーロード:宝箱フラグメント用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", treasurimage)//キャラ画像送信用
        bundle.putString("KEY_POSITION2", backimage)//背景画像送信用
        bundle.putString("KEY_POSITION3", text)//テキスト送信用
        treasureChestFragment.arguments = bundle
        return treasureChestFragment
    }

    private fun batlefragmentdisplay(){//バトルフラグメント表示メソッド(1,2両用)
        supportFragmentManager.beginTransaction().apply{
            if (GameFlagBranch[FlagY].flag == 0){//バトル1の場合
                remove(batle)//更新のための削除
                batle = BattleFragment()//再定義
                replace(R.id.BatleFrame, batle)//表示処理
                if (GameFlagBranch[FlagY].sikai.size == 3){
                    remove(command3)
                    command3 =  Command3Fragment()
                    add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
                }else if (GameFlagBranch[FlagY].sikai.size == 5){
                    remove(command5)
                    command5 =  Command5Fragment()
                    add(R.id.BatleFrame, command5)
                }
                getItem(GameFlagBranch[FlagY].imageName, GameFlagBranch[FlagY].sikai, GameFlagBranch[FlagY].sikai.size)
            }else if (GameFlagBranch[FlagY].flag == 2){//バトル2の場合
                remove(batle3)
                batle3 = Battle3Fragment()
                replace(R.id.BatleFrame, batle3)
                getItem(GameFlagBranch[FlagY].imageName, GameFlagBranch[FlagY].text)
            }
            addToBackStack(null);
            commit()
        }
        val packageName = packageName //packageName取得
        val imageId = resources.getIdentifier(GameFlagBranch[FlagY].back, "drawable", packageName) //リソースIDのを取得
        binding.haikei.setBackgroundResource(imageId) //画像のリソースIDで画像表示
    }

    private fun commandviewchangeB1(){//コマンドが正解していたら表示切り替え(バトルフラグメント1用）
        GameFlagBranch[FlagY].sikai[FlagX] = GameFlagBranch[FlagY].sikai[FlagX] *10
        batlefragmentdisplay()
    }

    private fun commandviewresetB1(){//不正解時コマンド表示リセット(バトルフラグメント1用）
        var i = 0
        if (FlagX != 0){
            while (i < FlagX) {
                GameFlagBranch[FlagY].sikai[i] = GameFlagBranch[FlagY].sikai[i] /10
                i++
            }
        }
        batlefragmentdisplay()
    }

    private fun commandviewchangeB2(){//コマンドが正解していたら表示切り替え(バトルフラグメント2用）
        viewanswer.add(GameFlagBranch[FlagY].sikai[FlagX])
        batlefragmentdisplay()
        supportFragmentManager.beginTransaction().apply {
            if (GameFlagBranch[FlagY].sikai.size == 3) {
                remove(command3)
                command3 = Command3Fragment()
                add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
            } else if (GameFlagBranch[FlagY].sikai.size == 5) {
                remove(command5)
                command5 = Command5Fragment()
                add(R.id.BatleFrame, command5)
            }
            getItem(GameFlagBranch[FlagY].imageName, viewanswer, GameFlagBranch[FlagY].sikai.size)
            commit()
        }
    }

    private fun commandviewresetB2(){//不正解時コマンド表示リセット(バトルフラグメント2用）
        supportFragmentManager.beginTransaction().apply {
            if (GameFlagBranch[FlagY].sikai.size == 3) {
                remove(command3)
            } else if (GameFlagBranch[FlagY].sikai.size == 5) {
                remove(command5)
            }
            commit()
        }
    }

    private fun npcfragmentdisplay(){//NPCフラグメント表示メソッド
        timer.cancel()
        timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
        binding.RedButton.visibility = View.INVISIBLE;
        binding.BlueButton.visibility = View.INVISIBLE;
        binding.GreenButton.visibility = View.INVISIBLE;
        supportFragmentManager.beginTransaction().apply{
            remove(npc)//更新のための削除
            npc = NPCFragment()
            replace(R.id.haikei, npc)
            getItem(GameFlagBranch[FlagY].imageName, GameFlagBranch[FlagY].back, GameFlagBranch[FlagY].text)
            commit()
        }
        binding.titleButton.visibility = View.INVISIBLE;
        FlagY++
    }

    private fun dungeonchange(int: Int){//list切り替えメソッド
        if (int == 0){
            subFlagY = FlagY + 1
            subFlagX = FlagX
            subGameFlagBranch = GameFlagBranch
            FlagY = 0
            FlagX = 0
            GameFlagBranch = getdungeon1(resources)
            flagbranch()
        }else if(int == 1){
            FlagY = subFlagY
            FlagX = subFlagX
            GameFlagBranch = subGameFlagBranch
            flagbranch()
        }
    }

    private fun movefragmentdisplay(){//moveフラグメント表示メソッド
        timer.cancel()
        timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
        notbuttonveiw()
        supportFragmentManager.beginTransaction().apply{
            remove(moveFragment)//更新のための削除
            moveFragment = MoveFragment()
            replace(R.id.haikei, moveFragment)
            //getItem(enemy[FlagY], back[FlagY],text[FlagY])
            commit()
        }
    }

    private fun treasurechsetfragmentdisplay(){//treasureフラグメント表示メソッド
        timer.cancel()
        timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
        notbuttonveiw()
        supportFragmentManager.beginTransaction().apply{
            remove(treasureChestFragment)//更新のための削除
            treasureChestFragment = TreasureChestFragment()
            replace(R.id.haikei, treasureChestFragment)
            treasuregetItem(GameFlagBranch[FlagY].imageName, GameFlagBranch[FlagY].back,GameFlagBranch[FlagY].text)
            commit()
        }
        FlagY++
    }

    fun flagbranch() {//フラグメント分岐
        buttonveiw()
        if (GameFlagBranch[FlagY].flag == 1){
            npcfragmentdisplay()//NPCフラグメント表示
            batlefragmentdisplay()//バトルフラグメント表示
        }else if (GameFlagBranch[FlagY].flag == 0 || GameFlagBranch[FlagY].flag == 2){
            batlefragmentdisplay()//バトルフラグメント表示
        }else if(GameFlagBranch[FlagY].flag == 3){
            dungeonchange(0)//ダンジョンにlistを変更
        }else if(GameFlagBranch[FlagY].flag == 4){
            dungeonchange(1)//メインにlistを変更
        }else if(GameFlagBranch[FlagY].flag == 5){
            movefragmentdisplay()//moveフラグメント表示
        }else if(GameFlagBranch[FlagY].flag == 6){
            treasurechsetfragmentdisplay()//moveフラグメント表示
        }
    }

    fun notbuttonveiw() {//ボタン非表示
        binding.titleButton.visibility = View.INVISIBLE;
        binding.RedButton.visibility = View.INVISIBLE;
        binding.BlueButton.visibility = View.INVISIBLE;
        binding.GreenButton.visibility = View.INVISIBLE;
    }

    fun buttonveiw() {//ボタン再表示
        binding.titleButton.visibility = View.VISIBLE;
        binding.RedButton.visibility = View.VISIBLE;
        binding.BlueButton.visibility = View.VISIBLE;
        binding.GreenButton.visibility = View.VISIBLE;
    }



    override fun moveright() {//moveフラグメントから右方向受け取り
        FlagY++
        flagbranch()
    }

    override fun moveleft() {//moveフラグメントから左方向受け取り
        FlagY = FlagY + 2
        flagbranch()
    }

    override fun moveforward() {//moveフラグメントから前方向受け取り
        FlagY = FlagY + 3
        flagbranch()
    }

    override fun onClickNext() {//NPCフラグメントからイベントを受け取れます。
        timer.start()
        flagbranch()
        /*if (GameFlagBranch[FlagY].flag == 1){
            //NPCフラグメント表示
            npcfragmentdisplay()
        }else if (GameFlagBranch[FlagY].flag != 1){
            timer.start()
            buttonveiw()
            //バトルフラグメント表示
            batlefragmentdisplay()
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GameFlagBranch = getGameFlagBranch1(resources)

        //ここからタイマーフラグメント、バトルフラグメント表示------------------------------------------
        npc = NPCFragment()
        timerF = TimerFragment()
        batle = BattleFragment()
        batle3 = Battle3Fragment()
        command3 =  Command3Fragment()
        command5 =  Command5Fragment()
        moveFragment = MoveFragment()
        treasureChestFragment = TreasureChestFragment()
        flagbranch()
        //ここまでタイマーフラグメント、バトルフラグメント表示-----------------------------------------

        //ここからテストボタン-----------------------------------------------------------------------
        binding.timerText.text = "3:00"
        timer = MyCountDownTimer((3 * 60) * 1000, 100)
        timer.start()
        //binding.testbutton.visibility = View.INVISIBLE;
        binding.testbutton.setOnClickListener {
            timer.cancel()
            clear()
            /*var a = getGameFlagBranch1(resources)
            Log.d("test", a.size.toString())
            Log.d("test", a[2].flag.toString())
            Log.d("test", a[2].imageName)
            Log.d("test", a[2].sikai.size.toString())
            Log.d("test", a[2].back)
            Log.d("test", a[2].text)*/
            //binding.RedButton.visibility = View.INVISIBLE;
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
        //ここまでテストボタン-----------------------------------------------------------------------

        //ここからボタン判定-------------------------------------------------------------------------
        fun hanntei(hand: Int){
            //判定式と正解に１０をかけることで表示が増やせる（例・・・sikai = 10 = hand*10）
            if (GameFlagBranch[FlagY].sikai[FlagX] == hand) {
                binding.timerText.text = "正解"
                if (GameFlagBranch[FlagY].flag == 0){
                    commandviewchangeB1()
                }else{
                    commandviewchangeB2()
                }
                FlagX++
                if (FlagX==GameFlagBranch[FlagY].sikai.size){
                    viewanswer = arrayListOf<Int>()
                    FlagX = 0
                    FlagY++
                    if (FlagY >= GameFlagBranch.size){
                        FlagY = 0
                        timer.cancel()
                        clear()
                    }else{
                        flagbranch()
                    }
                }
            }else {
                //binding.timerText.text = "不正解"
                viewanswer = arrayListOf<Int>()
                if (GameFlagBranch[FlagY].flag == 0){
                    commandviewresetB1()
                }else{
                    commandviewresetB2()
                }
                FlagX = 0
                timer.cancel()
                timer = MyCountDownTimer((minute * 60 + second - 10) * 1000, 100)
                timer.start()
            }
        }
        //ここまでボタン判定-------------------------------------------------------------------------

        //ここからメニューボタン現在機能未定----------------------------------------------------------
        binding.titleButton.setOnClickListener{
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.BatleFrame, timerF)
                commit()
            }
        }
        //ここまでメニューボタン---------------------------------------------------------------------

        lateinit var mp: MediaPlayer
        //赤ボタン----------------------------------------------------------------------------------
        binding.RedButton.setOnClickListener{
            hanntei(1)
            mp = MediaPlayer.create(this, R.raw.se_magic10);
            mp.start();
        }
        //赤ボタンここまで---------------------------------------------------------------------------

        //青ボタン----------------------------------------------------------------------------------
        binding.BlueButton.setOnClickListener{
            hanntei(2)
            mp = MediaPlayer.create(this, R.raw.bubble_attack1);
            mp.start();
        }
        //青ボタンここまで---------------------------------------------------------------------------

        //緑ボタン----------------------------------------------------------------------------------
        binding.GreenButton.setOnClickListener{
            hanntei(3)
            mp = MediaPlayer.create(this, R.raw.heavy_punch1);
            mp.start();
        }
        //緑ボタンここまで---------------------------------------------------------------------------
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        _binding = ActivitySubBinding.inflate(inflater, container, false)
//        return binding2.root
//    }
    //現在使用予定なし
    /*override fun onResume() {
        super.onResume()
        if (FlagY != 0){
            fragmentdisplay()
        }
    }*/

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {}
}