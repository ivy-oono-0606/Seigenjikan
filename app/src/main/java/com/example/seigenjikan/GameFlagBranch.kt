package com.example.seigenjikan

import android.content.res.Resources
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader

@Serializable
class GameFlagBranch(
    val flag:Int,
    val imageName:String,
    val sikai:ArrayList<Int>,
    val back:String,
    val text:String
    )

fun getGameFlagBranch1(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("GameFlagBranch1.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun getdungeon1(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("dungeon1.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}