package com.cm.github.entity

import com.google.gson.annotations.SerializedName

class GithubEntity {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("login")
    var login: String = ""

    @SerializedName("avatar_url")
    var avatar_url: String = ""
}