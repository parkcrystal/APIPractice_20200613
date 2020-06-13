package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.phis.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.emailEdt
import kotlinx.android.synthetic.main.activity_sign_up.signUpBtn
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

//    이메일과 닉네임의 중복검사 결과를 저장하는 변수
    var isEmailDuplOk = false
    var isNickNameDuplOk = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//      회원가입 버튼 누르기
        signUpBtn.setOnClickListener {

//            이메일 중복검사 통과 + 닉네임 중복검사 통과했는지 확인!
            if(!isEmailDuplOk) {
//                이메일 중복검사 통과 못함.
                Toast.makeText(mContext, "이메일 중복검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()


//                뒤의 로직 실행하지 않고 이 함수를 강제 종료.
                return@setOnClickListener
            }

            if(!isNickNameDuplOk) {
//                닉네임 중복검사 통과 못함.
                Toast.makeText(mContext, "닉네임 중복검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()


//                뒤의 로직 실행하지 않고 이 함수를 강제 종료.
                return@setOnClickListener
            }

//            여기가 실행이 되면 => 강제종료 두번을 모두 피했다. => (이메일과 닉네임 중복검사를 모두 통과함.)

//            입력한 이메일 / 비번 / 닉네임을 들고 서버에 가입 신청.
            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()
            val nickname = nickNameEdt.text.toString()


//            서버에 /user - PUT으로 요청 => ServerUtil을 통해 요청하자.
            ServerUtil.putRequestLogin(mContext, email, pw, nickname, object : ServerUtil.JsonResponseHandler{

                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {


                    }
                    else {

                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        }

                    }


                }

            })

            
        }


//      닉네임 에디트 변경 시, 재중복 검사.
        nickNameEdt.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickNameCheckBtnResultTxt.text = "닉네임 중복검사를 해주세요."
                isNickNameDuplOk = false
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
                isEmailDuplOk = false
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
                            isNickNameDuplOk = true
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
//                       중복검사 결과를 true로 변경
                            isEmailDuplOk = true
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
