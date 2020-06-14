package com.phis.apipractice_20200613.utils

import android.content.Context

class ContextUtil {


    companion object {

        //      메모장의 파일 이름에 대응되는 개념으로 만든 상수
        val prefName = "APIPracticePreference"

        val USER_TOKEN = "USER_TOKEN"

        //      토큰 저장 기능
        fun setUserToken(context: Context, token: String) {

//            저장할때 사용할 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//            열어준 메모장의 USER_TOKEN 항목에 받아온 token에 든 값을 저장.
            pref.edit().putString(USER_TOKEN, token).apply()

        }

 //         저장된 토큰 불러오기
        fun getUserToken(context: Context): String {

//            저장할때 사용한 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//          열어준 메모장의  USER_TOKEN 항목에 저장된 token값을 꺼내서 리턴
            return pref.getString(USER_TOKEN, "")!!    // USER_TOKEN에 아무것도 없으면 ""으로 처리. 따라서, !! (널이 아니다) 처리해줌.
            
        }


    }

}