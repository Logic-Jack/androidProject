package com.teamlink.teamactivityviewer.ui.clubs

import android.graphics.Path.Direction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.databinding.FragmentDashboardBinding
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.clubs.adapter.ClubListAdapter
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository

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
        val root: View = binding.root
        viewModel.onCreate()

        val adapter = ClubListAdapter(){
            val bundle = Bundle()
            bundle.putString("clubId", it.Id)
            val action = ClubListFragmentDirections.actionDashboardFragmentToClubDetailFragment()
            root.findNavController().navigate(action)
        }

        var list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        DataProvider.getInstance().clubs.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    override fun onStart() {
        var repo = LoginRepository(LoginDataSource())

        if (repo.user == null){
            val action = ClubListFragmentDirections.actionDashboardFragmentToLoginFragment()
            // Fragment().findNavController().navigate(action)
            val navController = (activity as MainNavActivity).findNavController(R.id.nav_host_fragment)
            if (navController.currentDestination?.label != "fragment_login"){
                try {
                    navController.navigate(action)
                }catch (ex: Exception){
                    throw ex
                }
            }

        }else{
            viewModel.onStart()
        }

        super.onStart()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}