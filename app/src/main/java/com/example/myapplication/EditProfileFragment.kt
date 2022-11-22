package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.container.HomeContainerFragment
import com.example.myapplication.container.UserContainerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        auth = Firebase.auth

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditProfile = view.findViewById<Button>(R.id.btn_edit_profile)

        val profileName = view.findViewById<EditText>(R.id.profile_name)
        val profilePhone = view.findViewById<EditText>(R.id.profile_phone_num)


        val currentUser = auth.currentUser
        var db = Firebase.firestore

        val docRef = db.collection("students").document(currentUser?.email.toString())

        btnEditProfile.setOnClickListener {
            db.collection("students").document(currentUser?.email.toString())
                .update(mapOf(
                    "nickname" to profileName.text.toString(),
                    "phonenum" to profilePhone.text.toString()
                ))
            Log.d("mytag","all button select")
            (parentFragment as UserContainerFragment).Profile()

        }

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    view.findViewById<TextView>(R.id.profile_email).text =
                        currentUser?.email.toString()
                }
            }
            // 값이 없으면 널 리턴, 실패한거는
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }




    }

}