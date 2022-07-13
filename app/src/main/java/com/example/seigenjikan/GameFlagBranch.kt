package com.example.seigenjikan

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class GameFlagBranch :RealmObject() {
    @PrimaryKey
    var id:Long = 0
    var data:Long = 0
}