package com.phis.apipractice_20200613

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.phis.apipractice_20200613.utils.ContextUtil
import com.phis.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {


    }

    override fun setValues() {

//        3초 뒤에 다음화면으로 이동.
        val myHandler = Handler()       // 안드로이드가 주는 Handler로 선택.
        myHandler.postDelayed({

//            시간이 지난 뒤, 실행할 내용       *

            /* 토큰의 유효성 검증 */
//            자동 로그인? => 안한다면,무조건 로그인 화면으로.
//                        => 한다고 하면, 저장된 토큰이 있나? => 있다면 => 서버에서 사용자 정보를 가져오는가?
//          ==> 정보를 가져오기까지 성공하면 => 메인으로.

            if(ContextUtil.isAutoLogin(mContext)) {

                if (ContextUtil.getUserToken(mContext) != "") {


//                    자동로그인 ok, 토큰도 ok => 서버에 유효한 토큰인지 물어보자! ( 내정보 가져오나)
                    ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{

                        override fun onResponse(json: JSONObject) {

                            val code = json.getInt("code")
                            if (code == 200) {
//                                정보도 잘 얻어옴 => 이 때만, 메인화면으로 진입.

                                val myIntent = Intent(mContext, MainActivity::class.java)
                                startActivity(myIntent)
                                finish()

                            }

                        }

                    })

                }
                else {
//                    자동로그인 체크만 찍고, 로그인은 안한 상황

                    val myIntent = Intent(mContext, LoginActivity::class.java)
                    startActivity(myIntent)
                    finish()

                }
            }
            else {

                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
                finish()
            }

        }, 3000)


    }

}
