package com.teamlink.teamactivityviewer.ui.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    private lateinit var clubs: Flow<List<ClubEntity>>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _viewModel = ViewModelProvider(this)[ClubListViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.onCreate()

        clubs = viewModel.clubService.all()

        val adapter = ClubListAdapter(){
            val bundle = Bundle()
            bundle.putString("clubId", it.Id)
            val action = ClubListFragmentDirections.actionDashboardFragmentToClubDetailFragment()
            root.findNavController().navigate(action)
        }

        var list = binding.list
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this.context)

        clubs.asLiveData().observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    override fun onStart() {

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            var application = activity?.application
            if (application != null){
                var repo = LoginRepository(LoginDataSource(), application)
                // repo.logout()
                repo.getUser()
                if (repo.user == null){
                    viewModel.viewModelScope.launch(Dispatchers.Main) {
                        val action = ClubListFragmentDirections.actionDashboardFragmentToLoginFragment()
                        try {
                            findNavController().navigate(action)
                        }catch (ex: Exception){
                            throw ex
                        }
                    }
                }else{
                    viewModel.onStart()
                }
            }
        }

        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}