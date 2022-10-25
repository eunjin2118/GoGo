package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.myapplication.container.HomeContainerFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allButton = view.findViewById<ImageButton>(R.id.all_btn)
        val careerButton = view.findViewById<ImageButton>(R.id.career_btn)
        val compButton = view.findViewById<ImageButton>(R.id.comp_btn)
        val schoolButton = view.findViewById<ImageButton>(R.id.school_btn)

//        allButton.setOnClickListener { view ->
//            Log.d("mytag","all button select")
//            (parentFragment as HomeContainerFragment).changeFragment(AllFragment())
//        }

        allButton?.setOnClickListener { view ->
            Log.d("mytag","all button select")
            (parentFragment as HomeContainerFragment).toAll()
        }

        careerButton?.setOnClickListener { view ->
            Log.d("mytag","career button select")
            (parentFragment as HomeContainerFragment).toCareer()
        }

        compButton?.setOnClickListener { view ->
            Log.d("mytag","comp button select")
            (parentFragment as HomeContainerFragment).toComp()
        }

        schoolButton?.setOnClickListener { view ->
            Log.d("mytag","school button select")
            (parentFragment as HomeContainerFragment).toSchool()
        }

    }


}