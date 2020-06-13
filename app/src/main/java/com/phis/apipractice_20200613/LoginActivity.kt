package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.phis.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import kotlin.math.log

//서버에서 받아온 응답처리.
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {



        loginBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            
//      실제로 서버에 두개의 변수를 전달해서 로그인 시도
//      별개의 클래스 (ServerUtil)에 서버 요청 기능을 만들고, 화면에서는 이를 사용.

            ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, object: ServerUtil.JsonResponseHandler
            {
                override fun onResponse(json: JSONObject) {
                    Log.d("화면에서 보는 응답", json.toString())
//                    서버에서 보내온 응답의 내용 분석 => 화면에 반영.


//                    제일 큰 중괄호에 달린 code라는 이름이 붙은 Int를 받아서 codeNum 에 저장.
                    val codeNum = json.getInt("code")

                    if (codeNum == 200) {
//                        로그인 성공

                        val data = json.getJSONObject("data")
                        val user = data.getJSONObject("user")
                        val loginUserNickname = user.getString("nick_name")
                        val loginUserEmail = user.getString("email")



                        runOnUiThread {
                        //로그인 한사람 이메일 토스트로 출력
                            Toast.makeText(mContext, "${loginUserNickname}님 환영합니다.", Toast.LENGTH_SHORT).show()
                            Toast.makeText(mContext, "${loginUserEmail}님 환영합니다.", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else {

//                        그 외의 숫자 : 로그인 실패
//                        실패 사유 : Message에 적힌 String을 확인하자. => Toast로 출력.
                        val message = json.getString("message")
                        
//                        인터넷 연결 쓰레드가 아닌,  UI 담당 쓰레드가 토스트를 띄우도록 처리 (비밀번호 틀렸을 경우, 앱이 죽는것을 방지)
                        runOnUiThread {
//                     서버가 알려준 실패사유를 그대로 토스트로 띄우기
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            
                        }

                        
                    }


                }
            })


        }

    }

    override fun setValues() {

    }

}
