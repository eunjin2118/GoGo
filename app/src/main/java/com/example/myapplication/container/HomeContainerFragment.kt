package com.example.myapplication.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.CommentFragment
import com.example.myapplication.HomeFragment
import com.example.myapplication.R
import com.example.myapplication.tabfragment.AllFragment
import com.example.myapplication.tabfragment.CareerFragment
import com.example.myapplication.tabfragment.CompetitionFragment
import com.example.myapplication.tabfragment.SchoolFragment

class HomeContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().add(R.id.container, HomeFragment()).commit()
    }

//    fun changeFragment(fragment: Fragment) {
//        childFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
//    }


    fun toAll() {
        childFragmentManager.beginTransaction().replace(R.id.container, AllFragment()).commit()
    }
    fun toCareer() {
        childFragmentManager.beginTransaction().replace(R.id.container, CareerFragment()).commit()
    }
    fun toComp() {
        childFragmentManager.beginTransaction().replace(R.id.container, CompetitionFragment()).commit()
    }
    fun toSchool() {
        childFragmentManager.beginTransaction().replace(R.id.container, SchoolFragment()).commit()
    }

    fun toComment(writeId: String){
        childFragmentManager.beginTransaction().replace(R.id.container, CommentFragment.newInstance(writeId)).commit()
    }
}