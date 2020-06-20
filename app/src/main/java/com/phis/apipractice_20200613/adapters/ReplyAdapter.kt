package com.phis.apipractice_20200613.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.phis.apipractice_20200613.R
import com.phis.apipractice_20200613.datas.TopicReply

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

//        목록에서 뿌려줄 데이터 꺼내오기
        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘
        contentTxt.text = data.content

        return row
    }

}