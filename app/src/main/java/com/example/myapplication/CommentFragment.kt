package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CommentListAdapter
import com.example.myapplication.item.CommentListItem
import com.example.myapplication.item.ListItem
import com.example.myapplication.item.PostListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = view.findViewById<RecyclerView>(R.id.comment_list)
        val cdata = view.findViewById<EditText>(R.id.comment_text)
        auth = Firebase.auth
        val db = Firebase.firestore
        val writeId = requireArguments().getString("write_id")!!
        dataList?.layoutManager = LinearLayoutManager(requireContext())

        val currentUser = auth.currentUser
        val docRef = db.collection("students").document(currentUser?.email.toString())

        var name = ""
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    name = document.data!!.get("nickname").toString()
                }
            }

        view.findViewById<Button>(R.id.add_comment).setOnClickListener {
            val write = hashMapOf(
                "name" to name,
                "content" to cdata.text.toString()
            )

            db.collection("writes")
                .document(writeId)
                .collection("comments")
                .document()
                .set(write)
                .addOnSuccessListener {
                    cdata.setText("")
                }
        }

        db.collection("writes")
            .document(writeId)
            .collection("comments")
            .get()
            .addOnSuccessListener { documents ->
                val commentItemList = arrayListOf<CommentListItem>()
                for (comment in documents){
                    commentItemList.add(CommentListItem(comment.data!!.get("name").toString(), comment.data!!.get("content").toString()))
                }
                val commentListAdapter = CommentListAdapter(commentItemList)
                dataList?.adapter = commentListAdapter
            }
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