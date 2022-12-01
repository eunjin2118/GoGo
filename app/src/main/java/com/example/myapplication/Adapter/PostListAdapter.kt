package com.example.myapplication.Adapter

import android.graphics.Color
import android.graphics.Color.LTGRAY
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.item.PostListItem
import com.example.myapplication.R
import com.example.myapplication.container.HomeContainerFragment

class PostListAdapter(val itemList: ArrayList<PostListItem>, val fragment: Fragment):
    RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    private var hearCount:Boolean = false

    // (1) 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)

        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.kind.text = itemList[position].kind
        holder.title.text = itemList[position].title
        holder.content.text = itemList[position].content

        holder.comment_btn.setOnClickListener {
            Log.d("mytag","${holder.comment_btn.toString()}가 클릭됨")
            // itemClickListener.onClick(it, position)
            val id = itemList[holder.adapterPosition].id
            (fragment as HomeContainerFragment).toComment(id)
        }

        holder.heart_count.setOnClickListener {
            Log.d("mytag","공감 클릭됨")
            if(!hearCount){
                holder.heart_count.text = "공감 ♥"
//                holder.heart_count.setBackgroundColor(Color.LTGRAY)
                hearCount=true
            }else{
                holder.heart_count.text = "공감 ♡"
//                holder.heart_count.setBackgroundColor(Color.LTGRAY)
                hearCount=false
            }


        }
        
    }
    // (4) 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.user_id)
        val kind: TextView = itemView.findViewById(R.id.post_kind)
        val title: TextView = itemView.findViewById(R.id.post_title)
        val content: TextView = itemView.findViewById(R.id.post_story)
        val comment_btn: Button = itemView.findViewById(R.id.comment_btn)
        val heart_count: Button = itemView.findViewById(R.id.heart_btn)
    }

    /*
    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener*/
}