package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.container.UserContainerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class EditProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val storage = Firebase.storage
    private var storageRef = storage.reference

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

    fun showPermissionContextPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("전자액자에서 사진을 선택하려면 권한이 필요합니다.")
            .setPositiveButton("동의하기", {_, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            })
            .setNegativeButton("취소하기",{ _,_ ->})
            .create()
            .show()
    }
    fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2000) {
            if(resultCode == Activity.RESULT_OK) {
                val selectedImageURI: Uri = data?.data!!
                Log.d("mytag", selectedImageURI.toString())
                val inputStream =
                    requireActivity().contentResolver.openInputStream(selectedImageURI)!!

                // val contentResolver: ContentResolver = requireActivity().contentResolver
                // val mimeTypeMap = MimeTypeMap.getSingleton()
                // val extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(selectedImageURI))
                // Log.d("mytag", extension.toString())
                val fileRef = storageRef.child("${auth.currentUser!!.email}/profile")

                // val stream = FileInputStream(File(selectedImageURI.path))
                fileRef.putStream(inputStream).addOnSuccessListener {
                    Log.d("mytag", "success")
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 권한이 부여 된 것입니다.
                    // 허용 클릭 시
                    navigatePhotos()
                } else {
                    // 거부 클릭시
                    Toast.makeText(activity,"권한을 거부했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditProfile = view.findViewById<Button>(R.id.btn_edit_profile)

        val btnUploadProfile = view.findViewById<Button>(R.id.profile_upload_btn)
        btnUploadProfile.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    // READ_EXTERNAL_STORAGE의 권한이 PERMISSION_GRANTED와 같다면..
                    //TODO 권한이 잘 부여되었을 때상황, 갤러리에서 사진을 선택하는 코드 구현
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // 권한을 명시적으로 거부한 경우 true
                    // 처음보거나, 다시묻지 않음을 선택한 경우 false
                    //TODO 교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    // 처음봤을때 띄워줌
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                }
            }
        }

        val profileName = view.findViewById<EditText>(R.id.profile_name)
        val profilePhone = view.findViewById<EditText>(R.id.profile_phone_num)

        val currentUser = auth.currentUser
        var db = Firebase.firestore

        val docRef = db.collection("students").document(currentUser?.email.toString())

        btnEditProfile.setOnClickListener {
            if(profileName.text.toString().length != 0 || profilePhone.text.toString().length != 0){
                db.collection("students").document(currentUser?.email.toString())
                    .update(mapOf(
                        "nickname" to profileName.text.toString(),
                        "phonenum" to profilePhone.text.toString()
                    ))
                (parentFragment as UserContainerFragment).Profile()
            } else {
                Toast.makeText(activity, " 이름 또는 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            Log.d("mytag","all button select")

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