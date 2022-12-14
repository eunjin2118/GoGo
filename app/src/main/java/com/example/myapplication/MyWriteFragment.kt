package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.MyWriteListAdapter
import com.example.myapplication.item.ListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyWriteFragment : Fragment() {
    private  lateinit var  auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_write, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = view.findViewById<RecyclerView>(R.id.rv_list)

        // val deleteBtn = view.findViewById<RecyclerView>(R.id.my_write_delete)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        val db = Firebase.firestore

        // 레이아웃 매니저와 어댑터 설정
        dataList?.layoutManager = LinearLayoutManager(requireContext())
        // dataList?.adapter = listAdapter

        db.collection("writes")
            .whereEqualTo("id", currentUser?.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                val itemList = arrayListOf<ListItem>()
                for (document in documents) {
                    Log.d("mytag", "${document.id} => ${document.data}")
                    // 아이템 추가

                    itemList.add(ListItem(document.id, document.data!!.get("title").toString(), document.data!!.get("category").toString()))
                }
                val listAdapter = MyWriteListAdapter(itemList, db)
                dataList?.adapter = listAdapter

                db.collection("writes")
                    .whereEqualTo("id", currentUser?.email.toString())
                    .addSnapshotListener { documents, e ->
                        Log.d("mytag", "in addSnapshotListener")
                        val itemList = arrayListOf<ListItem>()
                        for (document in documents!!) {
                            Log.d("mytag", "${document.id} => ${document.data}")
                            // 아이템 추가

                            itemList.add(ListItem(document.id, document.data!!.get("title").toString(), document.data!!.get("category").toString()))
                        }
                        val listAdapter = MyWriteListAdapter(itemList, db)
                        dataList?.adapter = listAdapter
                    }
            }

//
//        deleteBtn.setOnClickListener{
//            db.collection("writes")
//
//        }

    }
}