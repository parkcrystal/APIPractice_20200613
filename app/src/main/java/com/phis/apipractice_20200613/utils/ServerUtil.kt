package com.phis.apipractice_20200613.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

//화면(액티비티)에서 서버로 보냄.
class ServerUtil {

    //    어느 객체인지 관계 없이 기능/값만 잘 사용하면 되는 것들을 모아두는 영역
//    JAVA => static에 대응되는 개념
    companion object {


//     어느 서버로 가야하는지 (HOST 주소) 적어두는 변수

        val BASE_URL = "http://15.165.177.142"

        /*   좋아요 / 싫어요 찍기   */
        fun postRequestReplyLikeOrDislike(context: Context, replyId:Int, isLike:Boolean, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply_like"

            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API가 헤더를 요구하면 여기서 첨부하자
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)

                }

            })

        }


        /*   의견 남기기   */
        fun postRequestReply(
            context: Context,
            topicId: Int,
            content: String,
            handler: JsonResponseHandler?
        ) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header(
                    "X-Http-Token",
                    ContextUtil.getUserToken(context)
                )  // API가 헤더를 요구하면 여기서 첨부하자
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)

                }

            })


        }


        fun postRequestVote(context: Context, sideId: Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header(
                    "X-Http-Token",
                    ContextUtil.getUserToken(context)
                )  // API가 헤더를 요구하면 여기서 첨부하자
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)

                }

            })

        }


        /*   주제 상세 정보 확인:  원하는 주제의 상세정보 보기 => 몇번 주제인지 화면에서 받아와야 함.   */
        fun getRequestTopicDetail(context: Context, topicId: Int, handler: JsonResponseHandler?) {

//            또 필요함.
            val client = OkHttpClient()


//          4번주제? /topic/4  , 2번? /topic/2
            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()


//         첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

//          Request를 만들어서 최종 전송 정보 마무리.
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context))   //헤더를 요구하면 추가해야 함.
                .build()



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


        /*   진행중인 주제 목록 확인  */
        fun getRequestV2MainInfo(context: Context, handler: JsonResponseHandler?) {

//            또 필요함.
            val client = OkHttpClient()


//            진행중인 주제목록 확인 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder 로 만듦.
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()


//         첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

//          Request를 만들어서 최종 전송 정보 마무리.
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context))   //헤더를 요구하면 추가해야 함.
                .build()



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


        /*     사용자 정보조회    */
        fun getRequestMyInfo(context: Context, handler: JsonResponseHandler?) {

//            또 필요함.
            val client = OkHttpClient()


//            사용자 정보조회 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder 로 만듦.
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()


//         첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

//          Request를 만들어서 최종 전송 정보 마무리.
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context))   //헤더를 요구하면 추가해야 함.
                .build()



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


        /* <GET 방식> */
        fun getRequestDuplicatedCheck(
            context: Context,
            checkType: String,
            inputVal: String,
            handler: JsonResponseHandler?
        ) {

//            또 필요함.
            val client = OkHttpClient()

//            <GET 방식> 은 어디로 갈지 주소 + 어떤 데이터를 보낼지 같이 표시됨.
//            주소를 만들 때, 데이터 첨부까지 같이 진행!

//            중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder 로 만듦.
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
//            만든 주소 변수에 파라미터 첨부
            urlBuilder.addEncodedQueryParameter("type", checkType)
            urlBuilder.addEncodedQueryParameter("value", inputVal)

//         첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

//          Request를 만들어서 최종 전송 정보 마무리.
            val request = Request.Builder()
                .url(urlString)
                .get()
//                .header()   헤더를 요구하면 추가해야 함.
                .build()



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


//     서버에 로그인 요청 해주는 함수
//     context / handler 필수로 적어주자.
//     둘 사이에, 화면에서 넘겨줘야하는 자료들을 추가로 적어줌. => id, pw를 받아오자.

        /* <POST 방식> */
        fun postRequestLogin(
            context: Context,
            id: String,
            pw: String,
            handler: JsonResponseHandler?
        ) {


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

        fun putRequestLogin(
            context: Context,
            email: String,
            pw: String,
            nick: String,
            handler: JsonResponseHandler?
        ) {


//          이 안드로이드 앱이 클라이언트로 동작하도록 도와주는 클래스를 객체화 함.
            val client = OkHttpClient()

//          어떤 기능을 수행하러 가는지 주소 완성. => http://15.165.177.142/user
            val urlString = "${BASE_URL}/user"


//          서버에 들고갈 데이터(파라미터)를 첨부 => intent에 putExtra 하듯이. =>  POST: FormData에 담자.
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()

//            Request 정보를 완성해주자. => 화면으로 따지면, Intent 객체를 만드는 행위.
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
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