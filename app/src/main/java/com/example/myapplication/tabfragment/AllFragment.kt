package com.example.myapplication.tabfragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.PostListAdapter
import com.example.myapplication.R
import com.example.myapplication.item.PostListItem
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class AllFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val storage = Firebase.storage
    private var storageRef = storage.reference
    var count : Boolean = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = view?.findViewById<RecyclerView>(R.id.post_list)

        auth = Firebase.auth

        val db = Firebase.firestore
        val currentUser = auth.currentUser

        dataList?.layoutManager = LinearLayoutManager(requireContext())

        db.collection("writes")
            .get()
            .addOnSuccessListener { documents ->
                val postItemList = arrayListOf<PostListItem>()
                for (document in documents){
                    Log.d("mytag", "${document.id} => ${document.data}")

                    postItemList.add(PostListItem(
                        document.id,
                        document.data!!.get("nickname").toString(),
                        document.data!!.get("category").toString(),
                        document.data!!.get("title").toString(),
                        document.data!!.get("content").toString()))
                }
                val postListAdapter = PostListAdapter(postItemList, requireParentFragment())
                dataList?.adapter = postListAdapter




                /*
                postListAdapter.setItemClickListener(object: PostListAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        Toast.makeText(view.context,
                            "버튼 클릭됨", Toast.LENGTH_SHORT).show()
                        (parentFragment as HomeContainerFragment).toComment()
                    }
                })*/
            }
    }

}