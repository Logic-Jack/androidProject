package com.teamlink.teamactivityviewer.ui.data.model

class RefreshTokenRequest {

    constructor(refreshToken: String, Token: String) {
        this.refreshToken = refreshToken
        this.Token = Token
    }

    val refreshToken: String
    val Token: String
}