package com.phis.apipractice_20200613.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.phis.apipractice_20200613.EditReplyActivity
import com.phis.apipractice_20200613.R
import com.phis.apipractice_20200613.datas.Topic
import com.phis.apipractice_20200613.datas.TopicReply
import com.phis.apipractice_20200613.utils.ServerUtil
import com.phis.apipractice_20200613.utils.ServerUtil.JsonResponseHandler
import org.json.JSONObject

class ReplyAdapter(val mContext: Context, val resId: Int, val mList: List<TopicReply>) :
    ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        tempRow?.let {


        }.let {

            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }

        var row = tempRow!!

//        XML 에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val replyBtn = row.findViewById<Button>(R.id.replyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

//        목록에서 뿌려줄 데이터 꺼내오기
        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘
        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content

        replyBtn.text = "답글 : ${data.replyCount}"
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

/*        좋아요 / 싫어요 이벤트 처리     */

//            좋아요 API 호출 => 좋아요 누르기 / 취소 처리
        likeBtn.setOnClickListener {

            ServerUtil.postRequestLikeOrDislike(mContext, data.id, true, object : JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    화면에 변경된 좋아요/싫어요 갯수 반영(응용)
                    val dataObj = json.getJSONObject("data")
                    val reply = json.getJSONObject("reply")

//                    목록에서 꺼낸 data 변수의 객체를 통째로 바꾸는건 불가능.
//                    var로 바꿔서 통째로 바꿔도 => 목록에는 반영되지 않음.
//                    data = TopicReply.getTopicReplyFromJson(reply)
                    
//                    목록에서 꺼낸 data 변수의 좋아요 갯수 / 싫어요 갯수를 직접 변경
                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

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

            ServerUtil.postRequestLikeOrDislike(mContext, data.id, true, object : JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                }
            })
        }

        return row
    }

}