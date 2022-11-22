package com.example.myapplication.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.item.CommentListItem
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentListAdapter(val itemList: ArrayList<CommentListItem>):
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>(){

    // (1) 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

        val date = view.findViewById<TextView>(R.id.comment_date);

        // 현재 시간을 Date 타입으로 변환


        val tDate = Date(System.currentTimeMillis())
        val tDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm E", Locale("ko", "KR"))
        val strDate = tDateFormat.format(tDate)
        date.text = strDate+"요일"
        Log.d("mytag",strDate)

        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
//        holder.date.text = itemList[position].date
        holder.content.text = itemList[position].content

    }
    // (4) 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.comment_user_name)
        val content: TextView = itemView.findViewById(R.id.comment_content)
//        val date: TextView = itemView.findViewById(R.id.comment_date)
    }
}