package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.container.GoTempleContainerFragment
import com.example.myapplication.container.HomeContainerFragment
import com.example.myapplication.container.UserContainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        var bnv_main = findViewById(R.id.bnv_main) as BottomNavigationView

        bnv_main.run {
            setOnNavigationItemReselectedListener {
                when(it.itemId){
                    R.id.item_fragment1 -> {
                        val TodayGoTemple = GoTempleContainerFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TodayGoTemple).commit()
                    }
                    R.id.item_fragment2 -> {
                        val containerFragment = HomeContainerFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.fl_container, containerFragment).commit()
                    }
                    R.id.item_fragment3 -> {
                        val WriteFragment = WriteFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.fl_container, WriteFragment).commit()
                    }
                    R.id.item_fragment4 -> {
                        val UserContainerFragment = UserContainerFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.fl_container, UserContainerFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.item_fragment2
        }

    }
}