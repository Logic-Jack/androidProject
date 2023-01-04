package com.teamlink.teamactivityviewer.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.databinding.FragmentClubDetailsBinding
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.events.EventListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClubDetailFragment: Fragment() {

    private var _binding: FragmentClubDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ClubDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[ClubDetailViewModel::class.java]

        _binding = FragmentClubDetailsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val id = requireArguments().getString("clubId")

        (activity as MainNavActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (id != null){
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                val club = viewModel.getClub(id)
                if (club != null){
                    val bitmap = viewModel.getrandomImage()
                    viewModel.viewModelScope.launch(Dispatchers.Main){
                        if (bitmap != null){
                            binding.imageClub.setImageBitmap(bitmap)
                        }

                        binding.name.text = club.Name
                        binding.desc.text = club.Description
                        binding.streetName.text = "${club.StreetName} ${club.HouseNumber}"
                        binding.location.text = "${club.PostalCode} ${club.City}"

                        binding.eventButton.setOnClickListener {
                            val action = ClubDetailFragmentDirections.actionClubDetailFragmentToEventListFragment(club.Id)
                            root.findNavController().navigate(action)
                        }
                    }
                }
            }
        }

        return root
    }
}