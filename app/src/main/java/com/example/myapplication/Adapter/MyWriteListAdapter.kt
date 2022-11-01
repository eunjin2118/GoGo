package com.example.myapplication.Adapter

import com.example.myapplication.item.ListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MyWriteListAdapter(val itemList: ArrayList<ListItem>):
    RecyclerView.Adapter<MyWriteListAdapter.ViewHolder>(){

    // (1) 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mywrite, parent, false)
        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.category.text = itemList[position].category
    }
    // (4) 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val category: TextView = itemView.findViewById(R.id.tv_category)
    }
}