package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phis.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        emailCheckBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "EMAIL", "inputEmail", object :ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            emailCheckBtnResultTxt.text = "사용해도 좋습니다."
                        }

    
                    }
                    else {
                        runOnUiThread {
                            emailCheckBtnResultTxt.text = "이미 사용중입니다. 다른 이메일로 다시 체크해주세요."
                        }
                    }

                }

            })


        }

    }

    override fun setValues() {

    }
}
