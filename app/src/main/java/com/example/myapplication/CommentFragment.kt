package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CommentListAdapter
import com.example.myapplication.item.CommentListItem

class CommentFragment : Fragment() {
    val commentItemList = arrayListOf<CommentListItem>()
    val commentListAdapter = CommentListAdapter(commentItemList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)
        val dataList = view?.findViewById<RecyclerView>(R.id.comment_list)

        dataList?.layoutManager = LinearLayoutManager(requireContext())
        dataList?.adapter = commentListAdapter

        commentItemList.add(CommentListItem("꼬꼬빼빼", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("미리미", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}