package com.teamlink.teamactivityviewer.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamlink.teamactivityviewer.databinding.FragmentEventListBinding
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.events.adapter.EventListAdapter

class EventListFragment: Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

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

        var clubId = bundle.getString("clubId")
        if (clubId == null){
            showMessage("service not available")
        }

        dashboardViewModel.onCreate(clubId!!)

        val adapter = EventListAdapter(){
            bundle.putString("eventId", it.Id)
            val action = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment()
            root.findNavController().navigate(action)
        }

        var list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        DataProvider.getInstance().events.observe(this.viewLifecycleOwner){
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