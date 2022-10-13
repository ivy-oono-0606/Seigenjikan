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
    val text:String,
    val Moving:Int
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

fun kounanido(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("kounanido.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun kounanido2(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("kounanido2.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun Randomenemychnge(str:String): List<GameFlagBranch> {
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun randomdungon(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("randomdungon.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun random1(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("random1.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun random2(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("random2.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}

fun randommimikku(resources:Resources): List<GameFlagBranch> {
    val assetManager = resources.assets //アセット呼び出し
    val inputStream = assetManager.open("randommimikku.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText() //データ
    val obj = Json(JsonConfiguration.Stable).parse(GameFlagBranch.serializer().list, str)
    return obj
}