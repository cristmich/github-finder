package com.cm.github.entity

import com.google.gson.annotations.SerializedName

class SearchEntity {
    @SerializedName("total_count")
    var total_count: Int = 0

    @SerializedName("items")
    var items: List<GithubEntity> = ArrayList()
}