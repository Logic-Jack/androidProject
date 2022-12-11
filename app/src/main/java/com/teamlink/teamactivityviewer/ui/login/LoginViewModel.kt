package com.teamlink.teamactivityviewer.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.Result
import com.teamlink.teamactivityviewer.ui.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val loginRepository: LoginRepository, application: Application) : AndroidViewModel(application) {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {

        // can be launched in a separate asynchronous job
        viewModelScope.launch(Dispatchers.IO) {
            var result = loginRepository.login(username, password)
            viewModelScope.launch(Dispatchers.Main) {
                if (result is Result.Success && result.data.succesfullLogin) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = result.data.userName!!))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        }
    }

    fun getUser(): String?{
        return loginRepository.user?.userId
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}