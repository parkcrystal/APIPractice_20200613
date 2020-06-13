package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setEvents()
        setValues()
    }

    override fun setEvents() {

        loginBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            
//      실제로 서버에 두개의 변수를 전달해서 로그인 시도

        }

    }

    override fun setValues() {

    }

}
