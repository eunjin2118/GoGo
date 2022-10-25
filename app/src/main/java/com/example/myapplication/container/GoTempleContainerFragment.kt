package com.example.myapplication.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.TodayGoTemple
import com.example.myapplication.tabfragment.CareerFragment
import com.example.myapplication.tabfragment.CompetitionFragment
import com.example.myapplication.tabfragment.SchoolFragment

class GoTempleContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().add(R.id.container, TodayGoTemple()).commit()
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

}