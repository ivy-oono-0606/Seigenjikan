package com.example.seigenjikan

import android.app.Activity
import android.content.res.Resources
import android.media.MediaPlayer
lateinit var mp: MediaPlayer

fun defBGMstartLoop(act: Activity){
    mp = MediaPlayer.create(act, R.raw.fabgm);
    //mp.setVolume(float ,float );
    mp.setLooping(true);//    ループ設定
    mp.start()
}

fun StoryOVBGMstartLoop(act: Activity){
    mp = MediaPlayer.create(act, R.raw.story);
    mp.setLooping(true);//    ループ設定
    mp.start()
}

fun GMOVBGMstartLoop(act: Activity){
    mp = MediaPlayer.create(act, R.raw.deadsound)
    mp.setLooping(false);//    ループ設定;
    mp.start()
}

fun CLEBGMstartLoop(act: Activity){
    mp = MediaPlayer.create(act, R.raw.mokuhyokuria);
    mp.setLooping(false);//    ループ設定;
    mp.start()
}

fun BGMstart(act: Activity, BGMID: Int){
    mp = MediaPlayer.create(act, BGMID);
    mp.start()
}

fun BGMRestart(){//BGM再開用
    mp.start()
}

fun BGMstop(){
    mp.stop()
}

fun BGMpause(){
    mp.pause()
}

fun BGMrelease(){
    mp.release()
}