package com.teamlink.teamactivityviewer.ui.personal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository

class PersonalInfoViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalInfoViewModel::class.java)) {
            return PersonalInfoViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(),
                    application = application),
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}