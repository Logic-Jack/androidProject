package com.teamlink.teamactivityviewer.ui.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teamlink.teamactivityviewer.MainNavActivity
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = LoginViewModelFactory(requireActivity().application).create(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        (activity as MainNavActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val root : View = binding.root

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        val navbar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        val back = activity?.onBackPressedDispatcher
        val callback = object:OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showLoginFailed("please login")
            }
        }
        back?.addCallback(viewLifecycleOwner, callback)

        if (navbar != null){
            navbar.visibility = View.GONE
        }

        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed("Login failed!")
            }

            if (loginResult.success != null) {
                val context = context
                if (context != null){
                    val userId = viewModel.getUser()
                    if (userId != null){
                        findNavController().popBackStack()
                    }
                }
            }
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                viewModel.login(username.text.toString(), password.text.toString())
            }
        }

        return root
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(activity?.applicationContext, errorString, Toast.LENGTH_LONG).show()

    }

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}