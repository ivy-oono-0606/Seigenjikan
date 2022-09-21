package com.example.seigenjikan

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings.Global.putString
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.ActivitySubBinding
import java.io.File
import java.util.*

class SubActivity : AppCompatActivity(),NPCFragment.NPCListener ,MoveFragment.MoveListener,TreasureChestFragment.TreasureChestListener, configFragment.configListener{
    private lateinit var binding: ActivitySubBinding
    private lateinit var timerF: TimerFragment
    private lateinit var batle: BattleFragment
    private lateinit var batle3: Battle3Fragment
    private lateinit var npc: NPCFragment
    private lateinit var config: configFragment
    private lateinit var command2: Command2Fragment
    private lateinit var command3: Command3Fragment
    private lateinit var command4: Command4Fragment
    private lateinit var command5: Command5Fragment
    private lateinit var moveFragment: MoveFragment
    private lateinit var treasureChestFragment: TreasureChestFragment
    var minute:Long = 0
    var second:Long = 0
    var testchnge = 0

    private var GameFlagBranch = listOf<GameFlagBranch>()
    //呼び出すフラグメント0＝バトル１、１＝NPC、２＝バトル２ 3＝ダンジョン突入 4＝ダンジョン脱出 5＝方向　6＝宝箱

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
            if(0 == 0){//ランダムモードの時に別処理
                gameover()
            }else if(1 == 1){

            }

        }
    }

    private fun gameover(){//ここから画面遷移--------------------------------------------------------
        val intent = Intent(this, GameOver::class.java)
        startActivity(intent)
    }
    private fun clear(){
        val intent = Intent(this, Clear::class.java)
        startActivity(intent)
    }

    private fun getItem(enemyimage: String, com: ArrayList<Int>, Siz: Int): Pair<Fragment?, Fragment?> {//バトルフラグメントへ値を渡せます（バトルフラグメント用）
        val bundle = Bundle()// Bundle（オブジェクトの入れ物）のインスタンスを作成する
        bundle.putString("KEY_POSITION", enemyimage)//敵画像送信用(bundleの後のPuによt~~は送る変数によって変える)
        bundle.putIntegerArrayList("KEY_POSITION2", com)//コマンド送信用
        batle.arguments = bundle// Fragmentに値をセットする
        if (Siz == 2){
            command2.arguments = bundle
            return Pair(batle, command2)
        }else if (Siz == 3){
            command3.arguments = bundle
            return Pair(batle, command3)
        }else if (Siz == 4){
            command4.arguments = bundle
            return Pair(batle, command4)
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

    private fun treasuregetItem(treasurimage: String ,backimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(宝箱フラグメント用)
        val bundle = Bundle()
        bundle.putString("KEY_POSITION", treasurimage)//宝箱画像送信用
        bundle.putString("KEY_POSITION2", backimage)//背景画像送信用
        bundle.putString("KEY_POSITION3", text)//テキスト送信用
        treasureChestFragment.arguments = bundle
        return treasureChestFragment
    }

    private fun getItem(SE: Boolean): Fragment? {//フラグメントへ値を渡せます(宝箱フラグメント用)
        val bundle = Bundle()
        bundle.putBoolean("KEY_POSITION", SE)//SE設定用送信用
        config.arguments = bundle
        return config
    }

    private fun Randomenemy(): String{ //敵ランダム生成（コマンドのみ）
        val range = (1..3)
        val str = "[\n" +
                "            {\n" +
                "                \"flag\": 0,\n" +
                "                \"imageName\": \"suraimu\",\n" +
                "                \"sikai\": ["+range.random()+","+range.random()+","+range.random()+"],\n" +
                "                \"back\": \"mori\",\n" +
                "                \"text\": \"\"\n" +
                "            }\n" +
                "        ]"
    return str
    }

    private fun batlefragmentdisplay(){//バトルフラグメント表示メソッド(1,2両用)
        supportFragmentManager.beginTransaction().apply{
            if (GameFlagBranch[FlagY].flag == 0){//バトル1の場合
                remove(batle)//更新のための削除
                batle = BattleFragment()//再定義
                replace(R.id.BatleFrame, batle)//表示処理
                if (GameFlagBranch[FlagY].sikai.size == 2){
                    remove(command2)
                    command2 =  Command2Fragment()
                    add(R.id.BatleFrame, command2)//バトルフラグメントの上に重ねて表示
                }else if (GameFlagBranch[FlagY].sikai.size == 3){
                    remove(command3)
                    command3 =  Command3Fragment()
                    add(R.id.BatleFrame, command3)
                }else if (GameFlagBranch[FlagY].sikai.size == 4){
                    remove(command4)
                    command4 =  Command4Fragment()
                    add(R.id.BatleFrame, command4)
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
        val imageId = resources.getIdentifier(GameFlagBranch[FlagY].back, "drawable", packageName) //リソースIDを取得
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
            if (GameFlagBranch[FlagY].sikai.size == 2) {
                remove(command2)
                command2 = Command2Fragment()
                add(R.id.BatleFrame, command2)//バトルフラグメントの上に重ねて表示
            }else if (GameFlagBranch[FlagY].sikai.size == 3) {
                remove(command3)
                command3 = Command3Fragment()
                add(R.id.BatleFrame, command3)//バトルフラグメントの上に重ねて表示
            }else if (GameFlagBranch[FlagY].sikai.size == 4) {
                remove(command4)
                command4 = Command4Fragment()
                add(R.id.BatleFrame, command4)//バトルフラグメントの上に重ねて表示
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
            if (GameFlagBranch[FlagY].sikai.size == 2) {
                remove(command2)
            }else if (GameFlagBranch[FlagY].sikai.size == 3) {
                remove(command3)
            } else if (GameFlagBranch[FlagY].sikai.size == 4) {
                remove(command4)
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
            treasurechsetfragmentdisplay()// treasureフラグメント表示
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
        FlagY += 2
        flagbranch()
    }

    override fun moveforward() {//moveフラグメントから前方向受け取り
        FlagY += 3
        flagbranch()
    }

    override fun onClickNext() {//NPCフラグメントからイベントを受け取れます。
        if(GameFlagBranch[FlagY].flag == 6){
            FlagY += GameFlagBranch[FlagY].sikai[0]
        }
        timer.start()
        flagbranch()
    }

    override fun backconfig() {//configフラグメントから戻る受け取り
        binding.titleButton.isClickable= true
        binding.RedButton.isClickable = true
        binding.BlueButton.isClickable = true
        binding.GreenButton.isClickable = true
        timer.start()
    }

    override fun SEconfig(bool:Boolean) {//configフラグメントからSE
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        pref.edit {
            putBoolean("SE",bool)
        }
    }

    override fun retire() {//configフラグメントからリタイア
        timer.cancel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GameFlagBranch = getGameFlagBranch1(resources)//json読み込み
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み

        //ここからタイマーフラグメント、バトルフラグメント表示
        npc = NPCFragment()
        timerF = TimerFragment()
        batle = BattleFragment()
        batle3 = Battle3Fragment()
        command2 =  Command2Fragment()
        command3 =  Command3Fragment()
        command4 =  Command4Fragment()
        command5 =  Command5Fragment()
        moveFragment = MoveFragment()
        treasureChestFragment = TreasureChestFragment()
        flagbranch()
        //タイマー設定
        binding.timerText.text = "3:00"
        timer = MyCountDownTimer((3 * 60) * 1000, 100)
        timer.start()

        //ここからテストボタン
        //binding.testbutton.visibility = View.INVISIBLE;
        binding.testbutton.setOnClickListener {
            println(GameFlagBranch)
            GameFlagBranch = Randomenemychnge(Randomenemy())
            FlagX = 0
            FlagY = 0
            flagbranch()
            testchnge = 1
            /*timer.cancel()
            clear()*/
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

        fun hanntei(hand: Int){//ここからボタン判定
            //判定式と正解に１０をかけることで表示が増やせる（例・・・sikai = 10 = hand*10）
            if (GameFlagBranch[FlagY].sikai[FlagX] == hand) {
                //binding.timerText.text = "正解"
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
                timer = MyCountDownTimer((minute * 60 + second - 9) * 1000, 100)
                timer.start()
            }
        }

        fun hanntei2(hand: Int){
            //判定式と正解に１０をかけることで表示が増やせる（例・・・sikai = 10 = hand*10）
            if (GameFlagBranch[FlagY].sikai[FlagX] == hand) {
                //binding.timerText.text = "正解"
                    commandviewchangeB1()
                FlagX++
                if (FlagX==GameFlagBranch[FlagY].sikai.size){
                    FlagX = 0
                    GameFlagBranch = Randomenemychnge(Randomenemy())
                    flagbranch()
                }
            }else {
                //binding.timerText.text = "不正解"
                viewanswer = arrayListOf<Int>()
                commandviewresetB1()
                FlagX = 0
                timer.cancel()
                timer = MyCountDownTimer((minute * 60 + second - 9) * 1000, 100)
                timer.start()
            }
        }

        binding.titleButton.setOnClickListener{//ここからメニューボタン現在機能未定
            //ボタン無力化
            binding.titleButton.isClickable=false
            binding.RedButton.isClickable = false
            binding.BlueButton.isClickable = false
            binding.GreenButton.isClickable = false
            timer.cancel()
            timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
            supportFragmentManager.beginTransaction().apply {
                    config = configFragment()
                getItem(pref.getBoolean("SE",false))
                    add(R.id.config, config)//バトルフラグメントの上に重ねて表示
                commit()
            }
        }

        lateinit var mp: MediaPlayer//メディア設定

        binding.RedButton.setOnClickListener{//赤ボタン
            if(testchnge == 1){
                hanntei2(1)
            }else{
                hanntei(1)
            }
            if(pref.getBoolean("SE",false)){
                mp = MediaPlayer.create(this, R.raw.se_magic10);
                mp.start();
            }
        }

        binding.BlueButton.setOnClickListener{//青ボタン
            if(testchnge == 1){
                hanntei2(2)
            }else{
                hanntei(2)
            }
            if(pref.getBoolean("SE",false)){
                mp = MediaPlayer.create(this, R.raw.bubble_attack1);
                mp.start();
            }
        }

        binding.GreenButton.setOnClickListener{//緑ボタン
            if(testchnge == 1){
                hanntei2(3)
            }else{
                hanntei(3)
            }
            if(pref.getBoolean("SE",false)){
                mp = MediaPlayer.create(this, R.raw.heavy_punch1);
                mp.start();
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {}
}