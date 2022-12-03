package com.teamlink.teamactivityviewer.ui.data

import android.speech.RecognizerResultsIntent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.ui.data.model.LoggedInUser
import com.teamlink.teamactivityviewer.ui.login.LoginResult
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val apiService = ApiService()

    fun login(username: String, password: String): Result<LoggedInUser>? {
        try {
            // TODO: handle loggedInUser authentication
                //apiService.loginForRetards(username, password)
            val content = apiService.login(username, password)
            if (content != null){
                val loginResponse = Gson().fromJson(content, LoggedInUser::class.java)
                return Result.Success(loginResponse)
            }

            return null
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun refreshToken(user: LoggedInUser) : Result<LoggedInUser>? {
        try {
            val content = apiService.refreshToken(user)
            if (content != null){
                val loginResponse = Gson().fromJson(content, LoggedInUser::class.java)
                return Result.Success(loginResponse)
            }

            return null
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout(userId: String?) {
        apiService.logOut(userId)
    }
}