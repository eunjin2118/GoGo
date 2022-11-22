package com.example.myapplication.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.EditProfileFragment
import com.example.myapplication.MyWriteFragment
import com.example.myapplication.R
import com.example.myapplication.UserFragment

class UserContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().add(R.id.container, UserFragment()).commit()
    }

    fun toMyWrite(){
        childFragmentManager.beginTransaction().replace(R.id.container, MyWriteFragment()).commit()
    }

    fun toEditProfile(){
        childFragmentManager.beginTransaction().replace(R.id.container, EditProfileFragment()).commit()
    }

    fun Profile(){
        childFragmentManager.beginTransaction().replace(R.id.container, UserFragment()).commit()
    }

}