package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CommentListAdapter
import com.example.myapplication.item.CommentListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    //val commentItemList = arrayListOf<CommentListItem>()
    //val commentListAdapter = CommentListAdapter(commentItemList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)

       // dataList?.layoutManager = LinearLayoutManager(requireContext())
       // dataList?.adapter = commentListAdapter

        /*
        commentItemList.add(CommentListItem("꼬꼬빼빼", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("미리미", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        commentItemList.add(CommentListItem("안드로이드", "와 미친 ㄷㄷ 대박이다", "2022-10-20"))
        */

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = view?.findViewById<RecyclerView>(R.id.comment_list)
        val cdata = view?.findViewById<EditText>(R.id.editText)

        auth = Firebase.auth
        val db = Firebase.firestore
        val writeId = requireArguments().getString("write_id")!!

        dataList?.layoutManager = LinearLayoutManager(requireContext())

        val write = hashMapOf(
            "content" to cdata
        )

        db.collection("comments")
            .document(writeId)
            .collection("data")
            .document()
            .set(write)

    }

    companion object {
        fun newInstance(writeId: String): CommentFragment {
            val fragment = CommentFragment()

            val args = Bundle()
            args.putString("write_id", writeId)
            fragment.arguments = args

            return fragment
        }
    }
}