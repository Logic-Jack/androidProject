package com.teamlink.teamactivityviewer.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository

class LoginViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(),
                application = application),
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}