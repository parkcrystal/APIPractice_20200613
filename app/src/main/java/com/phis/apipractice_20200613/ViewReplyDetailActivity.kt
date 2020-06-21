package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phis.apipractice_20200613.adapters.ReReplyAdapter
import com.phis.apipractice_20200613.datas.TopicReply
import com.phis.apipractice_20200613.datas.TopicReply.Companion.getTopicReplyFromJson
import com.phis.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_edit_reply.*
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    var mReplyId = -1
    lateinit var mReply : TopicReply

//    답글 목록을 담고있게 될 배열
    val reReplyList = ArrayList<TopicReply>()
//    목록을 뿌려줄 어댑터 변수
    lateinit var mReReplyAdapter : ReReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        val content = reReplyContentEdt.text.toString()

//        답글 등록 API 찾아보기 활용법 숙지
//        답글 등록 성공시 => 리스트뷰의 내용 새로고침
//        서버에서 다시 답글 목록을 받아와서 추가

        postReReplyBtn.setOnClickListener {

            val inputContent = reReplyContentEdt.text

            ServerUtil.postRequestReReply(mContext, mReplyId, content, object : ServerUtil.JsonResponseHandler {


                override fun onResponse(json: JSONObject) {




                }

            })

        }

    }

    override fun setValues() {

        mReplyId = intent.getIntExtra("reply_id", -1)
        
//        어댑터를 먼저 생성 => 답글 목록을 뿌려준다고 명시
        mReReplyAdapter = ReReplyAdapter(mContext, R.layout.topic_re_reply_list_item, reReplyList)
//        어댑터와 리스트뷰 연결
        reReplyListView.adapter = mReReplyAdapter
        
//        서버에서 의견 상세 현황 가져오기
        getReplyDetailFromServer()

    }

    fun getReplyDetailFromServer() {

        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object: ServerUtil.JsonResponseHandler{

            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

//               화면에 뿌려질 답글 목록 (JSONArray) 도 담아주자.
                val reReplies = reply.getJSONArray("replies")

                for (i in 0..reReplies.length() -1) {
//                    JSONArray 내부의 객체를 => TopicReply로 변환 => reReplyList에 추가
                    reReplyList.add(TopicReply.getTopicReplyFromJson(reReplies.getJSONObject(i)))
                }

                runOnUiThread {

                    sideTitleTxt.text = mReply.selectedSide.title
                    writerNickNameTxt.text = mReply.user.nickName
                    contentTxt.text = mReply.content

//                    notifyDataSetchange 필요함.
//                    서버에서 받아온 대댓글을 리스트뷰에 반영. => 리스트뷰의 내용 변경 감지 새로고침
                    mReReplyAdapter.notifyDataSetChanged()

                }

            }

        })

    }

}