package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WriteFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

        val db = Firebase.firestore
        val currentUser = auth.currentUser

        val view = inflater.inflate(R.layout.fragment_write, container, false)
        val regisBtn = view.findViewById<Button>(R.id.btn_regis)

        // 어댑터를 통해 스피너와 아이템 연결하기
        val sAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.category, android.R.layout.simple_spinner_dropdown_item);
        val categoryComboBox = view.findViewById<Spinner>(R.id.categoryComboBox)
        categoryComboBox.adapter = sAdapter

        var nickname = ""

        val docRef = db.collection("students").document(currentUser?.email.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                nickname = document.data!!.get("nickname").toString()
            }

            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        regisBtn.setOnClickListener {
            val title = view.findViewById<EditText>(R.id.input_title)
            val content = view.findViewById<EditText>(R.id.input_content)

            val email = currentUser?.email.toString()

            val write = hashMapOf(
                "id" to email,
                "nickname" to nickname,
                "title" to title.text.toString(),
                "content" to content.text.toString(),
                "category" to categoryComboBox.selectedItem.toString(),
            )

            db.collection("writes").document()
                .set(write)
                .addOnSuccessListener {
                    Log.d("mytag", "등록 성공")
                    title.setText("")
                    content.setText("")
                    categoryComboBox.setSelection(0)
                    Toast.makeText(activity, "글 등록 성공!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    e -> Log.d("mytag", "등록 실패")
                }
                regisBtn.isEnabled = false
            }

        return view
    }


}