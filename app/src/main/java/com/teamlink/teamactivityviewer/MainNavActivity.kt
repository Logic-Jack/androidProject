package com.teamlink.teamactivityviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamlink.teamactivityviewer.databinding.ActivityNavMainBinding
import com.teamlink.teamactivityviewer.ui.clubs.ClubListFragment

class MainNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction()
//            .add(R.id.nav_host_fragment, ClubListFragment()).commit()
    }
}