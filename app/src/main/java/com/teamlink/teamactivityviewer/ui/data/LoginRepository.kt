package com.teamlink.teamactivityviewer.ui.data

import com.teamlink.teamactivityviewer.ui.data.model.LoggedInUser
import okio.IOException
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        val id: String? = user?.userId
        user = null
        dataSource.logout(id)
    }

    fun login(username: String, password: String): Result<LoggedInUser>? {
        // handle login
        try {
            val result = dataSource.login(username, password)

            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }

            return result

        }catch (ex: Exception){
            throw ex
        }
    }

    fun refreshUserToken(): Result<LoggedInUser>? {

        try {
            if (user != null){
                val result = dataSource.refreshToken(user!!)

                if (result is Result.Success){
                    setLoggedInUser(result.data)
                }
                return result
            }

            return Result.Error(IOException("no user data"))
        }catch (ex: Exception){
            throw ex
        }
    }

    fun isUserLoggedIn() : Boolean {
        if (user != null && user!!.succesfullLogin && Instant.now().isBefore(user!!.validTill.toInstant())){
            return true
        }

        return false
    }

    fun getLoggedInUser() : LoggedInUser? {
        return user
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}