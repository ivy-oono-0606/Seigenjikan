package com.example.seigenjikan

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class SubActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        // Enables Always-on
        setAmbientEnabled()
    }
}