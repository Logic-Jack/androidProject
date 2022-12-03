package com.teamlink.teamactivityviewer.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamlink.teamactivityviewer.databinding.FragmentClubDetailsBinding
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.clubs.ClubDetailViewModel

class EventDetailFragment: Fragment() {

    private var _binding: FragmentClubDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dashboardViewModel =
            ViewModelProvider(this)[ClubDetailViewModel::class.java]

        _binding = FragmentClubDetailsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val bundle = Bundle()
        val id = bundle.getString("eventId")

        if (id != null){
            val event = DataProvider.getInstance().getEvent(id)
            if (event != null){
                binding.name.text = event.Name
                binding.desc.text = event.Description
                binding.streetName.text = "${event.streetName} ${event.HouseNumber}"
                binding.location.text = "${event.PostalCode} ${event.City}"

                binding.eventButton.setOnClickListener {

                }
            }
        }else{
        }


        return root
    }
}