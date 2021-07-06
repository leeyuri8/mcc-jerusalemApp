package com.example.cloud_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yrabdelrhmn.finalprojectmcc.FavoriteFragment

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_News -> {
                    replaceFragment(News())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_City -> {
                    replaceFragment(Cities())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_fevorit -> {
                    replaceFragment(Messages())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_notifi -> {
                    replaceFragment(FavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    replaceFragment(Profile())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.navigation_City
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer,
            fragment).commit()
    }
}