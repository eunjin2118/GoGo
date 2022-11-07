package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val rg = findViewById<RadioGroup>(R.id.radio_group)

        val db = Firebase.firestore

        supportActionBar?.hide()

        var grade = ""
        rg.setOnCheckedChangeListener { radioGroup, checkedId ->
            when(checkedId){
                R.id.radio_button_fg -> {
                    grade = "1학년"
                }
                R.id.radio_button_sg -> {
                    grade = "2학년"
                }
                R.id.radio_button_tg -> {
                    grade = "3학년"
                }
            }
        }

        signUpBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_input).text.toString()
            val password = findViewById<EditText>(R.id.password_input).text.toString()
            val nickname = findViewById<EditText>(R.id.nickname_input).text.toString()
            val phonenum = findViewById<EditText>(R.id.phonenum_input).text.toString()

            val student = hashMapOf(
                "nickname" to nickname,
                "grade" to grade,
                "phonenum" to phonenum
            )

            db.collection("students").document(email)
                .set(student)
                .addOnSuccessListener {
                    Log.d("mytag", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener{
                        e -> Log.d("mytag", "Error writing document", e)
                }

            signUpBtn.isEnabled = false
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "회원가입 되었습니다", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        Log.d("mytag", "회원가입 성공 ${user.toString()}")
                        finish()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("mytag", "회원 가입 실패 (사유 : ${it.exception})")
                        Toast.makeText(baseContext, "회원 가입 실패", Toast.LENGTH_SHORT).show()
                        signUpBtn.isEnabled = true // 버튼 다시 누르기
                    }
                }
        }


    }
}