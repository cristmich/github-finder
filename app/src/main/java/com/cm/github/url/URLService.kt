package com.cm.github.url

import com.cm.github.entity.GithubEntity
import com.cm.github.entity.SearchEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface URLService {
    @Headers("Accept: application/json")
    @GET("/users")
    abstract fun getGithubList(
        @Query("since") since: Int
    ): Call<ArrayList<GithubEntity>>

    @Headers("Accept: application/json")
    @GET("/search/users")
    abstract fun getGithubSearchList(
        @Query("q") q: String
    ): Call<SearchEntity>
}