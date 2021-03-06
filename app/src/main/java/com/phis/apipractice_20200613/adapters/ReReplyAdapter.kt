package com.phis.apipractice_20200613.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.phis.apipractice_20200613.EditReplyActivity
import com.phis.apipractice_20200613.R
import com.phis.apipractice_20200613.ViewReplyDetailActivity
import com.phis.apipractice_20200613.ViewTopicDetailActivity
import com.phis.apipractice_20200613.datas.Topic
import com.phis.apipractice_20200613.datas.TopicReply
import com.phis.apipractice_20200613.utils.ServerUtil
import com.phis.apipractice_20200613.utils.ServerUtil.JsonResponseHandler
import org.json.JSONObject

class ReReplyAdapter(val mContext: Context, val resId: Int, val mList: List<TopicReply>) :
    ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        tempRow?.let {


        }.let {

            tempRow = inf.inflate(R.layout.topic_re_reply_list_item, null)
        }

        var row = tempRow!!

//        XML 에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

//        목록에서 뿌려줄 데이터 꺼내오기
        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘
        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"

//        좋아요 / 싫어요 버튼 관련 표시
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        내 좋아요 / 싫어요 여부 표시
        if (data.isMyLike) {
//            내가 좋아요를 찍은 댓글일 경우
//            좋아요 빨간색 / 싫어요 회색
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

//            좋아요 글씨 색 : 빨간색 => res => colors => red를 사용
            likeBtn.setTextColor(mContext.resources.getColor(R.color.colorRed))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.colorDarkGray))
        }
        else if (data.isMyDislike) {
//            내가 싫어요를 찍은 댓글일 경우
//            좋아요 회색 / 싫어요 파란색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)
//            버튼 글씨색 설정
            likeBtn.setTextColor(mContext.resources.getColor(R.color.colorDarkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.colorBlue))

        }
        else {
//            아무것도 찍지 않은 경우
//            둘다 회색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

//            버튼 글씨색 설정
            likeBtn.setTextColor(mContext.resources.getColor(R.color.colorDarkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.colorDarkGray))
        }

/*        좋아요 / 싫어요 이벤트 처리     */

//            좋아요 API 호출 => 좋아요 누르기 / 취소 처리
        likeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

//                    화면에 변경된 좋아요/싫어요 갯수 반영(응용)
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")


//                    목록에서 꺼낸 data 변수의 객체를 통째로 바꾸는건 불가능.
//                    var로 바꿔서 통째로 바꿔도 => 목록에는 반영되지 않음.
//                    data = TopicReply.getTopicReplyFromJson(reply)
                    
//                    목록에서 꺼낸 data 변수의 좋아요 갯수 / 싫어요 갯수를 직접 변경

                    val likeCount = reply.getInt("like_count")
                    val dislikeCount = reply.getInt("dislike_count")

                    data.likeCount = likeCount
                    data.dislikeCount = dislikeCount

                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

//                    목록의 내용을 일부 변경
//                    어댑터.notifyDataSetChanged() 실행 필요함
//                    이미 어댑터 내부에 있는 상황 => 곧바로 notifyDataSetChanged() 실행 가능

//                    runOnUiThread로 처리 필요 => 어댑터 내부에선 사용 불가.
//                    대체제: Handler(Looper.getMainLooper()).post (UIThread 접근하는 방법)
                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }
                }

            })
        }

//            싫어요 API 호출 => 좋아요 누르기 / 취소 처리
        dislikeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, false, object : JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    화면에 변경된 좋아요/싫어요 갯수 반영(응용)
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

//                    목록에서 꺼낸 data 변수의 객체를 통째로 바꾸는건 불가능.
//                    var로 바꿔서 통째로 바꿔도 => 목록에는 반영되지 않음.
//                    data = TopicReply.getTopicReplyFromJson(reply)

//                    목록에서 꺼낸 data 변수의 좋아요 갯수 / 싫어요 갯수를 직접 변경
                    val likeCount = reply.getInt("like_count")
                    val dislikeCount = reply.getInt("dislike_count")

                    data.likeCount = likeCount
                    data.dislikeCount = dislikeCount

                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

//                    목록의 내용을 일부 변경
//                    어댑터.notifyDataSetChanged() 실행 필요함
//                    이미 어댑터 내부에 있는 상황 => 곧바로 notifyDataSetChanged() 실행 가능

//                    runOnUiThread로 처리 필요 => 어댑터 내부에선 사용 불가.
//                    대체제: Handler(Looper.getMainLooper()).post (UIThread 접근하는 방법)
                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }

                }
            })
        }

        return row
    }

}