package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        auth = Firebase.auth

        val signInBtn = findViewById<Button>(R.id.signIn_btn)

        val currentUser = auth.currentUser
        if(currentUser != null) {
            // 로그인된 사용자가 있는 경우 필요한 작업 진행 (ex: 그냥 로그인 화면 넘어가서 메인 화면으로 가기)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        signInBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.signIn_email).text.toString()
            val password = findViewById<EditText>(R.id.signIn_pwd).text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        // 성공
                        Log.d("mytag", "로그인 성공 ${it.toString()}")
                        Toast.makeText(baseContext, "로그인 셩굥", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Log.w("mytag", "로그인 실패 (사유 : ${it.exception})")
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                        signInBtn.isEnabled = true // 버튼 다시 누르기
                    }
                }
        }

        findViewById<Button>(R.id.signUp_btn).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }
}