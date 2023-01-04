package com.teamlink.teamactivityviewer.services

import android.content.res.Resources
import com.google.gson.Gson
import com.teamlink.teamactivityviewer.R
import com.teamlink.teamactivityviewer.ui.data.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class ApiService {
    // private val baseUrl: String = "https://finalwork-api.azurewebsites.net"//Resources.getSystem().getString(R.string.BaseUrl)
    private val baseUrl: String = "http://10.0.2.2:5070"//Resources.getSystem().getString(R.string.BaseUrl)

    private val mediaTypeJson: MediaType = "application/json; charset=utf-8".toMediaType()

    fun login(username :String, password :String) : String? {
        val url = "$baseUrl/Login/LoginUser"
        val requestBody = LoginRequest(username, password, false)

        lateinit var response: Response

        try {
            // response = get("https://finalwork-api.azurewebsites.net/Login/Test")
            response = post(url, requestBody)
            if (response.isSuccessful) {
                return response.body?.string()
            }
        }catch (ex: Exception){
            throw ex
        }finally {
            response.close()
        }

        return null
    }

    fun logOut(userId: String?) {
        val url = "$baseUrl/Login/logout/$userId"

        try {
            get(url)
        }catch (ex: Exception){
            throw ex
        }
    }

    fun refreshToken(user: LoggedInUser) : String?{
        val url = "$baseUrl/Refresh/RefreshToken"

        if (user.refreshToken != null && user.token != null){
            val requestBody = RefreshTokenRequest(user.refreshToken, user.token)
            lateinit var response: Response

            try {
                response = post(url, requestBody)
                if (response.isSuccessful) {
                    return response.body?.string()
                }
            }catch (ex: Exception){
                throw ex
            }finally {
                response.close()
            }
        }

        return null
    }

    fun getUserInfo(userId: String, token: String) : String? {
        val url = "$baseUrl/user/UserInfo/$userId"

        try {
            val response = getWithToken(url, token)
            if (response.isSuccessful){
                return response.body?.string()
            }
        }catch (ex: Exception) {
            throw ex
        }

        return null
    }

    fun getClubsFromUser(userId: String, token: String): String?{
        val url = "$baseUrl/club/GetClubsForUser/$userId"

        try {
            val response = getWithToken(url, token)
            if(response.isSuccessful){
                return response.body?.string()
            }
        }catch (ex: Exception){
            throw ex
        }

        return null
    }

    fun getEventsFromClub(clubId: String, token: String): String? {
        val url = "$baseUrl/event/GetEventsFromClub/$clubId"

        try {
            val response = getWithToken(url, token)
            if(response.isSuccessful){
                return response.body?.string()
            }
        }catch (ex: Exception){
            throw ex
        }

        return null
    }

    private fun post(url: String, body: Any): Response {
        val client = OkHttpClient().newBuilder().connectTimeout(20, TimeUnit.MILLISECONDS).build()
        val json = Gson().toJson(body)
        val bodyValue = json.toRequestBody(mediaTypeJson)

        val request = Request.Builder().url(url).post(bodyValue).build()

        try {
            return  client.newCall(request).execute()
        }catch (ex: Exception){
            throw ex
        }
    }

    fun getRandomImage(): ByteArray? {
        val url = "https://picsum.photos/350/150"
        lateinit var response: Response
        try {
            response = get(url)
            if (response.isSuccessful){
                return response.body?.bytes()
            }
        }catch (ex: Exception){
            println("oeps")
        }finally {
            response.close()
        }

        return null
    }

    private fun getWithToken(url: String, token: String): Response{
        val client = OkHttpClient().newBuilder().connectTimeout(20, TimeUnit.MILLISECONDS).build()

        val request = Request.Builder()
                .header("Authorization", "Bearer $token")
                .url(url).get()
                .build()

        try {
            return client.newCall(request).execute()
        }catch (ex: Exception){
            throw ex
        }
    }

    private fun get(url: String): Response{
        val client = OkHttpClient().newBuilder().connectTimeout(20, TimeUnit.MILLISECONDS).build()

        val request = Request.Builder().url(url).get().build()

        try {
            return client.newCall(request).execute()
        }catch (ex: Exception){
            throw ex
        }
    }

}