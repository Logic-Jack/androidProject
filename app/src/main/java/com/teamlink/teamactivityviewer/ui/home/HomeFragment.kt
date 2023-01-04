package com.teamlink.teamactivityviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val navbar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

        if (navbar != null){
            navbar.visibility = View.VISIBLE
        }

        return binding.root
    }
}