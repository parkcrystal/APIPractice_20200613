package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity: AppCompatActivity() {

    val mContext = this

    abstract fun setEvents()


    abstract fun setValues()
}