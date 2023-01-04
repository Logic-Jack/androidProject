package com.teamlink.teamactivityviewer.ui.data

import android.app.Application
import com.teamlink.teamactivityviewer.room.Services.UserService
import com.teamlink.teamactivityviewer.room.entity.UserEntity
import com.teamlink.teamactivityviewer.ui.data.model.LoggedInUser
import okio.IOException
import java.text.SimpleDateFormat
import java.time.Instant

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, application: Application) {

    var userService = UserService(application)
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        //user = getUserLoggedIn()
    }

    @JvmName("getUser1")
    fun getUser() : LoggedInUser? {
        if (user == null){
            user = return getUserLoggedIn()
        }

        return user
    }

    fun getUserId(): String? {
        if (user == null){
            user = getLoggedInUser()
        }

        return user?.userId
    }

    fun getToken() : String? {
        if (user == null){
            user = getLoggedInUser()
        }

        return user?.token
    }

    private fun getUserLoggedIn() : LoggedInUser? {
        var user = userService.get() ?: return null
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        if (user != null){
            return LoggedInUser(user.succesfullLogin, user.userId, user.userName, user.token, format.parse(user.validTill), user.refreshToken)
        }

        return null
    }

    fun logout() {
        val id: String? = user?.userId
        user = null
        userService.deleteAll()
        // dataSource.logout(id)
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
        if (user != null && user!!.succesfullLogin){
            return true
        }

        return false
    }

    fun getLoggedInUser() : LoggedInUser? {
        return user
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        if(loggedInUser.succesfullLogin){
            userService.deleteAll()
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            user = loggedInUser
            val userEntity = UserEntity(loggedInUser.userId!!, loggedInUser.succesfullLogin, loggedInUser.userName, loggedInUser.token, format.format(loggedInUser.validTill), loggedInUser.refreshToken)
            userService.insert(userEntity)
        }
    }
}