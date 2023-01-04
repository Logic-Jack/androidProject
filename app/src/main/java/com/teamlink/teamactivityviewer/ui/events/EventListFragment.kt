package com.teamlink.teamactivityviewer.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.databinding.FragmentEventListBinding
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.events.adapter.EventListAdapter

class EventListFragment: Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[EventListViewModel::class.java]

        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainNavActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val clubId = requireArguments().getString("clubId")
        if (clubId != null){
            viewModel.onCreate(clubId)
        }

        val adapter = EventListAdapter {
            val action = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(it.Id)
            root.findNavController().navigate(action)
        }

        val list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        viewModel.events.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        return root
    }

}