package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.container.GoTempleContainerFragment


class TodayGoTemple : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_today_go_temple, container, false)

        val career_btn = view.findViewById<Button>(R.id.btn_career)
        val comp_btn = view.findViewById<Button>(R.id.btn_competition)
        val school_btn = view.findViewById<Button>(R.id.btn_school)



        career_btn.setOnClickListener {
            Log.d("mytag","진로 버튼 선택됨")
            (parentFragment as GoTempleContainerFragment).toCareer()
        }
        comp_btn.setOnClickListener {
            Log.d("mytag","공모전 버튼 선택됨")
            (parentFragment as GoTempleContainerFragment).toComp()
        }
        school_btn.setOnClickListener {
            Log.d("mytag","학교생활 버튼 선택됨")
            (parentFragment as GoTempleContainerFragment).toSchool()
        }

        return view
    }

}