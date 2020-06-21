package com.phis.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phis.apipractice_20200613.datas.TopicReply
import com.phis.apipractice_20200613.datas.TopicReply.Companion.getTopicReplyFromJson
import com.phis.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    var myReplyId = -1
    lateinit var mReply : TopicReply

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        myReplyId = intent.getIntExtra("reply_id", -1)
        
//        서버에서 의견 상세 현황 가져오기

    }

    fun getReplyDetailFromServer() {

        ServerUtil.getRequestReplyDetail(mContext, myReplyId, object: ServerUtil.JsonResponseHandler{

            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

                runOnUiThread {

                    sideTitleTxt.text = mReply.selectedSide.title
                    writerNickNameTxt.text = mReply.user.nickName
                    contentTxt.text = mReply.content

                }

            }

        })

    }

}