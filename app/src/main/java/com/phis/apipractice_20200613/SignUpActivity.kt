package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


        nickNameCheckBtn.setOnClickListener {

            val inputNickName = nickNameEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "NICKNAME", inputNickName, object :ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {


                    Log.d("화면에서 보는 응답", json.toString())

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            nickNameCheckBtnResultTxt.text = "사용해도 좋은 닉네임입니다."
                        }


                    }
                    else {
                        runOnUiThread {
                            nickNameCheckBtnResultTxt.text = "중복된 닉네임입니다. 다른 닉네임을 사용해주세요."
                        }
                    }

                }

            })

        }

        emailCheckBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "EMAIL", inputEmail, object :ServerUtil.JsonResponseHandler {
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
