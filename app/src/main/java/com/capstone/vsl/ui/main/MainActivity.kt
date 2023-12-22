package com.capstone.vsl.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.capstone.vsl.R
import com.capstone.vsl.ui.main.moduls.ModulsFragment
import com.capstone.vsl.ui.main.news.NewsFragment
import com.capstone.vsl.ui.main.practice.PracticeFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Navigation Bar
        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Modules", R.drawable.ic_home_active_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Practice", R.drawable.ic_quiz_active_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "News", R.drawable.ic_book_active_24)
        )

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    replaceFragment(ModulsFragment())
                }
                2 -> {
                    replaceFragment(PracticeFragment())
                }
                3 -> {
                    replaceFragment(NewsFragment())
                }
            }
        }
        replaceFragment(ModulsFragment())
        bottomNavigation.show(1)

    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layoutFrame, fragment)
            .commit()
    }
}