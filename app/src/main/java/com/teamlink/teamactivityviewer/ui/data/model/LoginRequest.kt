package com.teamlink.teamactivityviewer.ui.data.model

//@Serializable
class LoginRequest {
    constructor(username: String, password: String, rememberMe: Boolean){
        UserName = username
        Password = password
        RememberMe = rememberMe
    }

    val UserName: String?
    val Password: String?
    var RememberMe: Boolean = false
}