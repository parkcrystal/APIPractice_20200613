package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

//      닉네임 에디트 변경 시, 재중복 검사.
        nickNameEdt.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickNameCheckBtnResultTxt.text = "닉네임 중복검사를 해주세요."
            }


        })

//      이메일 에디트 변경 시, 재중복 검사.
        emailEdt.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                글자가 변경된 시점에 실행되는 함수
//                Log.d("변경된 내용", s.toString())

//                이메일 중복검사 하라고 안내.
                emailCheckBtnResultTxt.text = "이메일 중복검사를 해주세요."
            }


        })


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
