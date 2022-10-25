package com.example.myapplication.com.example.myapplication

import com.example.myapplication.ListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CommentListItem
import com.example.myapplication.PostListItem
import com.example.myapplication.R

class CommentListAdapter(val itemList: ArrayList<CommentListItem>):
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>(){

    // (1) 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: CommentListAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.date.text = itemList[position].date
        holder.content.text = itemList[position].content
    }
    // (4) 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.comment_user_name)
        val content: TextView = itemView.findViewById(R.id.comment_content)
        val date: TextView = itemView.findViewById(R.id.comment_date)
    }
}