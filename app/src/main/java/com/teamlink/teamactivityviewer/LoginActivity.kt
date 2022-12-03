package com.teamlink.teamactivityviewer

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.add
import androidx.navigation.findNavController
import com.teamlink.teamactivityviewer.databinding.ActivityLoginBinding
import com.teamlink.teamactivityviewer.ui.login.LoginViewModel
import com.teamlink.teamactivityviewer.ui.login.LoginViewModelFactory
import com.teamlink.teamactivityviewer.ui.login.LoggedInUserView
import com.teamlink.teamactivityviewer.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    // private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_activity_login, LoginFragment()).commit()
    }
}