package com.phis.apipractice_20200613.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
    
 //    어느 객체인지 관계 없이 기능/값만 잘 사용하면 되는 것들을 모아두는 영역
//    JAVA => static에 대응되는 개념
    companion object {


//     어느 서버로 가야하는지 (HOST 주소) 적어두는 변수

        val BASE_URL = "http://15.165.177.142"

//     서버에 로그인 요청 해주는 함수
//     context / handler 필수로 적어주자.
//     둘 사이에, 화면에서 넘겨줘야하는 자료들을 추가로 적어줌. => id, pw를 받아오자.
        fun postRequestLogin(context: Context, id:String, pw:String, handler: JsonResponseHandler?) {


//          이 안드로이드 앱이 클라이언트로 동작하도록 도와주는 클래스를 객체화 함.
            val client = OkHttpClient()

//          어떤 기능을 수행하러 가는지 주소 완성. => http://15.165.177.142/user
            val urlString = "${BASE_URL}/user"


//          서버에 들고갈 데이터(파라미터)를 첨부 => intent에 putExtra 하듯이. =>  POST: FormData에 담자.
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

//            Request 정보를 완성해주자. => 화면으로 따지면, Intent 객체를 만드는 행위.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header()    API 가 헤더를 요구하면 여기서 첨부하자.
                .build()

//          startActivity 처럼 실제로 요청을 날리는 (클라이언트) 코드
//          요청에 대한 서버의 응답 처리도 같이 코딩

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    <실패>: 서버에 연결 자체를 실패했을 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    <성공>: 서버에 응답을 잘 받아왔을경우
//                    응답 중에서 body(내용물)을 string으로 저장

                    val bodyString = response.body!!.string()


//                    저장한 String을 JSONObject 양식으로 가공
//                   서버의 응답이 JSON 형태이기 때문.
                    val json = JSONObject(bodyString)


//                    화면 (액티비티)에 만들어낸 json변수를 전달
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)


                }


            })

        }



    }


//    서버통신의 응답 내용 (JSON) 을 화면으로 전달해주기 위한 인터페이스

    interface JsonResponseHandler {

        fun onResponse(json: JSONObject)

    }


}