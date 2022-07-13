package com.example.seigenjikan

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyDataBase : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        //初期化
        val config= RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()   //モデルクラスを変更した際にエラーが出なくなる
            .build()
        Realm.setDefaultConfiguration(config)
        //デフォルト設定として保存。
    }
}