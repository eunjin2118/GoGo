package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.LoginActivity
import com.example.myapplication.container.UserContainerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {
    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  //
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        auth = Firebase.auth

        // val currentUser = auth.currentUser


        //view.findViewById<TextView>(R.id.profile_name).text = currentUser.
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            auth.signOut()
            Toast.makeText(activity, "로그아웃 완료", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myWriteBtn = view.findViewById<Button>(R.id.my_write)
        val editProfile = view.findViewById<Button>(R.id.edit_profile)

        val currentUser = auth.currentUser
        var db = Firebase.firestore

        val docRef = db.collection("students").document(currentUser?.email.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    view.findViewById<TextView>(R.id.profile_name).text = document.data!!.get("nickname").toString()
                    view.findViewById<TextView>(R.id.profile_email).text = currentUser?.email.toString()
                    view.findViewById<TextView>(R.id.profile_phonenum).text = document.data!!.get("phonenum").toString()
                    view.findViewById<TextView>(R.id.profile_grade).text = document.data!!.get("grade").toString()

                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else { // id가 없을 때
                    Log.d(TAG, "No such document")
                }
            }
                // 값이 없으면 널 리턴, 실패한거는
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        myWriteBtn.setOnClickListener{
            Log.d("mytag", "내가 쓴 글 보러가기")
            (parentFragment as UserContainerFragment).toMyWrite()
        }

        editProfile.setOnClickListener {
            Log.d("mytag", "프로필 수정하기")
            (parentFragment as UserContainerFragment).toEditProfile()
        }


    }

}