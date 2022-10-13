package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.style.BulletSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.ActivitySubBinding
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
    var modechange = 0
    var killcount = 0
    var poisonT = 0
    var redbutton = 1
    var bluebutton = 2
    var greenbutton = 3
    var bundle = Bundle()
    lateinit var sp0: SoundPool

    private var GameFlagBranch = listOf<GameFlagBranch>()
    //呼び出すフラグメント0＝バトル１、１＝NPC、２＝バトル２ 3＝ステージ変更 4＝ステージ戻す 5＝方向　6＝宝箱 7=クリア

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
            if(modechange == 5){//ランダムモードの時に別処理
                clear()
            }else{
                gameover()
            }
        }
    }

    private fun gameover(){//ここから画面遷移--------------------------------------------------------
        BGMstop()
        BGMrelease()
        sp0.release()
        val intent = Intent(this, GameOver::class.java)
        startActivity(intent)
    }

    private fun clear(){
        BGMstop()
        BGMrelease()
        sp0.release()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        if(modechange == 1){
            pref.edit {
                putBoolean("S1",true)
                if (minute*60+second > pref.getLong("S1time",0)){
                    putLong("S1time",minute*60+second)
                }
            }
        }else if(modechange == 2){
            pref.edit {
                putBoolean("S2",true)
                if (minute*60+second > pref.getLong("S2time",0)){
                    putLong("S2time",minute*60+second)
                }
            }
        }else if(modechange == 3){
            pref.edit {
                putBoolean("S3",true)
                if (minute*60+second > pref.getLong("S3time",0)){
                    putLong("S3time",minute*60+second)
                }
            }
        }else if(modechange == 4){
            pref.edit {
                putBoolean("S4",true)
                if (minute*60+second > pref.getLong("S4time",0)){
                    putLong("S4time",minute*60+second)
                }
            }
        }else if(modechange == 5){
            pref.edit {
                putBoolean("S5",true)
                if (killcount > pref.getLong("S5kill",0)){
                    putInt("S5kill",killcount)
                }
            }
        }else if(modechange == 6){
            pref.edit {
                putBoolean("S6",true)
                if (minute*60+second > pref.getLong("S6time",0)){
                    putLong("S6time",minute*60+second)
                }
            }
        }
        val intent = Intent(this, Clear::class.java)
        startActivity(intent)
    }

    private fun getItem(enemyimage: String, com: ArrayList<Int>, Siz: Int): Pair<Fragment?, Fragment?> {//バトルフラグメントへ値を渡せます（バトルフラグメント用）
        bundle = Bundle()// Bundle（オブジェクトの入れ物）のインスタンスを作成する
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
        bundle = Bundle()
        bundle.putString("KEY_POSITION", enemyimage)//敵画像送信用
        bundle.putString("KEY_POSITION2", text)//テキスト送信用
        batle3.arguments = bundle
        return batle3
    }

    private fun getItem(charaimage: String, backimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(オーバーロード:NPCフラグメント用)
        bundle = Bundle()
        bundle.putString("KEY_POSITION", charaimage)//キャラ画像送信用
        bundle.putString("KEY_POSITION2", backimage)//背景画像送信用
        bundle.putString("KEY_POSITION3", text)//テキスト送信用
        npc.arguments = bundle
        return npc
    }

    private fun treasuregetItem(treasurimage: String ,backimage: String, text: String): Fragment? {//フラグメントへ値を渡せます(宝箱フラグメント用)
        bundle = Bundle()
        bundle.putString("KEY_POSITION", treasurimage)//宝箱画像送信用
        bundle.putString("KEY_POSITION2", backimage)//背景画像送信用
        bundle.putString("KEY_POSITION3", text)//テキスト送信用
        treasureChestFragment.arguments = bundle
        return treasureChestFragment
    }

    private fun getItem(SE: Boolean,AE: Boolean): Fragment? {//フラグメントへ値を渡せます(configフラグメント用)
        bundle = Bundle()
        bundle.putBoolean("KEY_POSITION", SE)//SE設定用送信用
        bundle.putInt("KEY_POSITION2", 0)//展開アクティビティ送信用
        bundle.putBoolean("KEY_POSITION3", AE)//SE設定用送信用
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
                "                \"Moving\": \"1\"\n" +
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

    private fun stagechange(int: Int){//list切り替えメソッド
        if (int == 0){
            subFlagY = FlagY + 1
            subFlagX = FlagX
            subGameFlagBranch = GameFlagBranch
            FlagX = 0
            if("random1" == GameFlagBranch[FlagY].text){
                val range = (0..6)
                FlagY = range.random()
                GameFlagBranch = random1(resources)
            }else if("random2" == GameFlagBranch[FlagY].text){
                val range = (0..10)
                FlagY = range.random()
                GameFlagBranch = random2(resources)
            }else if("randommimikku" == GameFlagBranch[FlagY].text){
                val range = (0..3)
                FlagY = range.random()
                GameFlagBranch = randommimikku(resources)
            }
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
        val packageName = packageName //packageName取得
        val imageId = resources.getIdentifier(GameFlagBranch[FlagY].back, "drawable", packageName) //リソースIDを取得
        binding.haikei.setBackgroundResource(imageId) //画像のリソースIDで画像表示
        supportFragmentManager.beginTransaction().apply{
            remove(batle)//更新のための削除
            batle = BattleFragment()//再定義
            remove(batle3)
            batle3 = Battle3Fragment()
            remove(moveFragment)//更新のための削除
            moveFragment = MoveFragment()
            replace(R.id.haikei, moveFragment)
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
            stagechange(0)//ダンジョンにlistを変更
        }else if(GameFlagBranch[FlagY].flag == 4){
            stagechange(1)//メインにlistを変更
        }else if(GameFlagBranch[FlagY].flag == 5){
            movefragmentdisplay()//moveフラグメント表示
        }else if(GameFlagBranch[FlagY].flag == 6){
            treasurechsetfragmentdisplay()// treasureフラグメント表示
        }else if(GameFlagBranch[FlagY].flag == 7){
            clear()
        }
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

    override fun onClickNext() {//宝フラグメントからイベントを受け取れます。
        if(GameFlagBranch[FlagY].flag == 6){
            //30でミミック
            val range = (1..100)
            if(GameFlagBranch[FlagY].sikai[0] == 1){//制限時間が伸びるギミック
                timer.cancel()
                timer = MyCountDownTimer((minute * 60 + second + GameFlagBranch[FlagY].sikai[1] ) * 1000, 100)
                timer.start()
                FlagY += GameFlagBranch[FlagY].Moving
            }else if(GameFlagBranch[FlagY].sikai[0] == 2){//毒トラップ
                poisonT = GameFlagBranch[FlagY].sikai[1]
                poison()
                FlagY += GameFlagBranch[FlagY].Moving
            }else if(range.random() <= 30 && GameFlagBranch[FlagY].sikai[0] == 10){//ミミックが出るギミック
                FlagY += GameFlagBranch[FlagY].sikai[1]
            }else{
                FlagY += GameFlagBranch[FlagY].Moving
            }
        }
        timer.start()
        flagbranch()
    }

    fun poison() {//毒の効果生成
        val range = (1..5)
        var int = range.random()
        if(int == 1){
            redbutton = 3
            bluebutton = 1
            greenbutton = 2
            binding.RedButton.setImageResource(R.drawable.greenbutton)
            binding.BlueButton.setImageResource(R.drawable.redbutton)
            binding.GreenButton.setImageResource(R.drawable.bluebutton)
        }else if(int == 2){
            redbutton = 2
            bluebutton = 1
            greenbutton = 3
            binding.RedButton.setImageResource(R.drawable.bluebutton)
            binding.BlueButton.setImageResource(R.drawable.redbutton)
            binding.GreenButton.setImageResource(R.drawable.greenbutton)
        }else if(int == 3){
            redbutton = 2
            bluebutton = 3
            greenbutton = 1
            binding.RedButton.setImageResource(R.drawable.bluebutton)
            binding.BlueButton.setImageResource(R.drawable.greenbutton)
            binding.GreenButton.setImageResource(R.drawable.redbutton)
        }else if(int == 4){
            redbutton = 1
            bluebutton = 3
            greenbutton = 2
            binding.RedButton.setImageResource(R.drawable.redbutton)
            binding.BlueButton.setImageResource(R.drawable.greenbutton)
            binding.GreenButton.setImageResource(R.drawable.bluebutton)
        }else if(int == 5){
            redbutton = 3
            bluebutton = 2
            greenbutton = 1
            binding.RedButton.setImageResource(R.drawable.greenbutton)
            binding.BlueButton.setImageResource(R.drawable.bluebutton)
            binding.GreenButton.setImageResource(R.drawable.redbutton)
        }
    }

    fun poisonresst(){
        redbutton = 1
        bluebutton = 2
        greenbutton = 3
        binding.RedButton.setImageResource(R.drawable.redbutton)
        binding.BlueButton.setImageResource(R.drawable.bluebutton)
        binding.GreenButton.setImageResource(R.drawable.greenbutton)
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

    override fun AEconfig(bool:Boolean) {//configフラグメントからSE
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//config読み込み
        pref.edit {
            putBoolean("AE",bool)
        }
    }

    override fun retire() {//configフラグメントからリタイア
        BGMrelease()
        timer.cancel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modechange = intent.getIntExtra("mode",1)

        if(modechange == 1){
            GameFlagBranch = getGameFlagBranch1(resources)//json読み込み
        }else if(modechange == 2){
            GameFlagBranch = getdungeon1(resources)
        }else if(modechange == 3){
            GameFlagBranch = kounanido2(resources)
        }else if(modechange == 4){
            GameFlagBranch = kounanido(resources)
        }else if(modechange == 5){
            GameFlagBranch = Randomenemychnge(Randomenemy())
        }else if(modechange == 6){
            GameFlagBranch = randomdungon(resources)
        }

        //タイマー設定
        if(modechange == 2){
            binding.timerText.text = "1:00"
            timer = MyCountDownTimer((1 * 60) * 1000, 100)
        }else{
            binding.timerText.text = "3:00"
            timer = MyCountDownTimer((3 * 60) * 1000, 100)
        }

        timer.start()

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

        //ここからテストボタン
        //binding.testbutton.visibility = View.INVISIBLE;
        binding.testbutton.setOnClickListener {
            poisonT = 5
            poison()
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
                    if (poisonT > 0){
                        poison()
                        poisonT--
                        if (poisonT == 0){
                            poisonresst()
                        }
                    }
                    viewanswer = arrayListOf<Int>()
                    FlagX = 0
                    FlagY += GameFlagBranch[FlagY].Moving
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
            if (GameFlagBranch[FlagY].sikai[FlagX] == hand) {
                //binding.timerText.text = "正解"
                    commandviewchangeB1()
                FlagX++
                if (FlagX==GameFlagBranch[FlagY].sikai.size){
                    FlagX = 0
                    killcount++
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
                getItem(pref.getBoolean("SE",true),pref.getBoolean("AE",true))
                    add(R.id.config, config)
                commit()
            }
        }

        //効果音
        var snd0=0
        var snd1=0
        var snd2=0
        val aa0= AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
        sp0=SoundPool.Builder().setAudioAttributes(aa0).setMaxStreams(2).build()
        snd0=sp0.load(this,R.raw.se_magic10,1)
        snd1=sp0.load(this,R.raw.bubble_attack1,1)
        snd2=sp0.load(this,R.raw.heavy_punch1,1)

        //エフェクト
        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.05f, 0.9f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.05f, 0.9f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.attackeffect, inflateX, inflateY).apply {
            duration = 200
            repeatCount = ObjectAnimator.INFINITE
        }

        fun efect(int: Int){
            if (int == 1){
                binding.attackeffect.setImageResource(R.drawable.redefect)
                binding.attackeffect.visibility = View.VISIBLE;
                binding.attackeffect.setAlpha(100);
                animator.start()
            }
            if(int == 2){
                binding.attackeffect.setImageResource(R.drawable.blueefect)
                binding.attackeffect.visibility = View.VISIBLE;
                binding.attackeffect.setAlpha(100);
                animator.start()
            }
            if(int == 3){
                binding.attackeffect.setImageResource(R.drawable.greenefect)
                binding.attackeffect.visibility = View.VISIBLE;
                binding.attackeffect.setAlpha(100);
                animator.start()
            }
            Handler().postDelayed({
                binding.attackeffect.visibility = View.INVISIBLE;
            }, 1000/5)
        }

        binding.RedButton.setOnClickListener{//赤ボタン
            if(modechange == 5){
                hanntei2(redbutton)
            }else{
                hanntei(redbutton)
            }

            if(pref.getBoolean("SE",true)){
                sp0.play(snd0,1.0f,1.0f,0,0,1.0f)
            }

            if(pref.getBoolean("AE",true)){
                efect(redbutton)
            }
        }

        binding.BlueButton.setOnClickListener{//青ボタン
            if(modechange == 5){
                hanntei2(bluebutton)
            }else{
                hanntei(bluebutton)
            }

            if(pref.getBoolean("SE",true)){
                sp0.play(snd1,1.0f,1.0f,0,0,1.0f)
            }

            if(pref.getBoolean("AE",true)){
                efect(bluebutton)
            }
        }

        binding.GreenButton.setOnClickListener{//緑ボタン
            if(modechange == 5){
                hanntei2(greenbutton)
            }else{
                hanntei(greenbutton)
            }

            if(pref.getBoolean("SE",true)){
                sp0.play(snd2,1.0f,1.0f,0,0,1.0f)
            }

            if(pref.getBoolean("AE",true)){
                efect(greenbutton)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        timer.start()
        StoryOVBGMstartLoop(this)
        if(modechange == 1){

        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        timer = MyCountDownTimer((minute * 60 + second) * 1000, 100)
        BGMrelease()
    }

    override fun onDestroy() {
        GameFlagBranch = listOf<GameFlagBranch>()
        subGameFlagBranch = listOf<GameFlagBranch>()
        super.onDestroy()
    }

    override fun onBackPressed() {}
}