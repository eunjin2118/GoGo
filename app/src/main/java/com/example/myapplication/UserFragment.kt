package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.container.UserContainerFragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.nio.ByteBuffer


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

        /// 스토리지 접근
        val storage = Firebase.storage
        // 스토리지 리퍼런스 객체 얻어오기 (이후 레퍼런스 객체를 이용하여 파일 업로드 다운로드 가능)
        var storageRef = storage.reference

        val myWriteBtn = view.findViewById<Button>(R.id.my_write)
        val editProfile = view.findViewById<Button>(R.id.edit_profile)

        val currentUser = auth.currentUser
        var db = Firebase.firestore

        val docRef = db.collection("students").document(currentUser?.email.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                val ref = storageRef.child(currentUser?.email.toString() + "/profile")
                val localFile = File("temp", "profile")
                // getFile 호출 이후 다운로드 시작
                val downloadTask = ref.getFile(localFile)

//                ref.listAll()
//                    .addOnSuccessListener(OnSuccessListener<ListResult> { result ->
//                        for (fileRef in result.items) {
//                            ref.child("${fileRef.name}").downloadUrl.addOnCompleteListener {
//                                if (it.isSuccessful) { // ALLIMGS는 key가 string, 값이 imageView인 해시맵이다.
//                                    Glide.with(this).asBitmap().load(it.result)
//                                        .into(object :BitmapImageViewTarget(AllIMGS["${fileRef.name}"]) {});
//
//                                }
//                            }
//                        }
//                    })

                downloadTask.addOnSuccessListener {

                    // 3. 그 바이트를 Bitmap 이미지로 변환하고
//                    val bytes = localFile.readBytes()
////                    val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
//                    var decryptedText: ByteArray = cipher.doFinal(bytes)
//                    val configBmp = Bitmap.Config.valueOf(bitmap.config.name)
//                    val bitmap_tmp = Bitmap.createBitmap(width, height, configBmp)
//                    val buffer = ByteBuffer.wrap(decryptedText)
//                    bitmap_tmp.copyPixelsFromBuffer(buffer)

                    // 4. 그 Bitmap을 ImageView로 소스로 설정하기
                }

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