package com.teamlink.teamactivityviewer.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.databinding.FragmentEventDetailsBinding
import com.teamlink.teamactivityviewer.ui.events.adapter.PeriodListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailFragment: Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventDetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[EventDetailViewModel::class.java]

        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        (activity as MainNavActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val id = requireArguments().getString("eventId")

        if (id != null){
            viewModel.oncreate(id)
        }

        val adapter = PeriodListAdapter()
        binding.periods.adapter = adapter
        binding.periods.layoutManager = LinearLayoutManager(this.context)

        viewModel.periods.observe(viewLifecycleOwner){
                adapter.submitList(it)
        }

        viewModel.event.observe(viewLifecycleOwner){
            val event = it
            if (event != null){
                binding.name.text = event.Name
                binding.desc.text = event.Description
                binding.streetName.text = "${event.StreetName} ${event.HouseNumber}"
                binding.location.text = "${event.PostalCode} ${event.City}"
                binding.periods
            }
            else{
                showMessage("data not available")
            }
        }

        return root
    }

    private fun showMessage(messageString: String) {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, messageString, Toast.LENGTH_LONG).show()
        }
    }
}