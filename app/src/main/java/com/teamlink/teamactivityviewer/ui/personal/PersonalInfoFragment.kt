package com.teamlink.teamactivityviewer.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.databinding.FragmentPersonalInfoBinding
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PersonalInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = PersonalInfoViewModelFactory(application = requireActivity().application).create(PersonalInfoViewModel::class.java)

        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)

        val navbar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

        if (navbar != null){
            navbar.visibility = View.VISIBLE
        }

        binding.logoutButton?.isEnabled = false
        binding.logoutButton?.setOnClickListener {
            binding.progressBar?.visibility = View.VISIBLE
            binding.logoutButton?.isEnabled = false
            viewModel.logout()
            findNavController().clearBackStack(R.id.clubDetailFragment)
            findNavController().clearBackStack(R.id.eventDetailFragment)
            findNavController().clearBackStack(R.id.eventListFragment)

        }

        viewModel.personalInfo.observe(viewLifecycleOwner, Observer {

            binding.progressBar?.visibility = View.INVISIBLE
            if (it != null){
                binding.firstname.text = it.firstName
                binding.lastname.text = it.lastName
                binding.emailAddress.text = it.emailAddress
                binding.postalCode.text = it.postalCode
                binding.city.text = it.city

                binding.logoutButton?.isEnabled = true
            }else{
                navbar?.selectedItemId = R.id.navigation_home
            }
        })

        val view = binding.root
        return view
    }

    override fun onStart() {

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val application = activity?.application
            if (application != null){
                val repo = LoginRepository(LoginDataSource(), application)
                // repo.logout()
                val user = repo.getUser()
                if (user == null){
                    viewModel.viewModelScope.launch(Dispatchers.Main) {
                        val action = PersonalInfoFragmentDirections.actionPersonalInfoToLoginFragment()
                        try {
                            findNavController().navigate(action)
                        }catch (ex: Exception){
                            throw ex
                        }
                    }
                }else{
                    viewModel.getPersonalInfo()
                }
            }
        }

        super.onStart()
    }



}