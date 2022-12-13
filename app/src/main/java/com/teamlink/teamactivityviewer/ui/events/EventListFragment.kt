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
import com.teamlink.teamactivityviewer.databinding.FragmentEventListBinding
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.events.adapter.EventListAdapter

class EventListFragment: Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private lateinit var events: LiveData<List<EventEntity>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[EventListViewModel::class.java]

        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = Bundle()

        val application = activity?.application
        if (application != null){
            events = EventService(application).all().asLiveData()
        }

        val clubId = requireArguments().getString("clubId")
        if (clubId == null){
            showMessage("service not available")
        }

        dashboardViewModel.onCreate(clubId!!)

        val adapter = EventListAdapter(){
            bundle.putString("eventId", it.Id)
            val action = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment()
            root.findNavController().navigate(action)
        }

        val list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        events.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    private fun showMessage(messageString: String) {
        Toast.makeText(activity?.applicationContext, messageString, Toast.LENGTH_SHORT).show()
    }
}