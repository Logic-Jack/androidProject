package com.teamlink.teamactivityviewer.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.databinding.FragmentDashboardBinding
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.ui.clubs.adapter.ClubListAdapter
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClubListFragment : Fragment() {


    private var _binding: FragmentDashboardBinding? = null
    private var _viewModel: ClubListViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _viewModel = ViewModelProvider(this)[ClubListViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        (activity as MainNavActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val root: View = binding.root

        val navbar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

        if (navbar != null){
            navbar.visibility = View.VISIBLE
        }

        val adapter = ClubListAdapter {

            val action = ClubListFragmentDirections.actionDashboardFragmentToClubDetailFragment(it.Id)
            root.findNavController().navigate(action)
        }

        val list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        viewModel.clubs.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    override fun onStart() {

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val application = activity?.application
            if (application != null){
                val repo = LoginRepository(LoginDataSource(), application)
                // repo.logout()
                val user = repo.getUser()
                if (user == null) {
                    viewModel.viewModelScope.launch(Dispatchers.Main) {
                        val action = ClubListFragmentDirections.actionDashboardFragmentToLoginFragment()
                        try {
                            findNavController().navigate(action)
                        }catch (ex: Exception){
                            throw ex
                        }
                    }
                }
                else {
                    viewModel.onStart(user)
                }

            }
        }

        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showMessage(messageString: String) {
        Toast.makeText(activity?.applicationContext, messageString, Toast.LENGTH_SHORT).show()
    }
}