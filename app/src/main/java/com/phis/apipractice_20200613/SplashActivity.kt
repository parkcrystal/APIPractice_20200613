package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : BaseActivity() {
    override fun setupEvents() {


    }

    override fun setValues() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }
}
