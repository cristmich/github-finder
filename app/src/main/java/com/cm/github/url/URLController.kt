package com.cm.github.url

import com.cm.github.entity.GithubEntity
import com.cm.github.entity.SearchEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class URLController {

    fun getGithubList(
        last: Int,
        urlCallback: URLCallback<ArrayList<GithubEntity>?>
    ) {
        val urlService = URLFactory().retrofit().create(URLService::class.java)
        val call = urlService.getGithubList(
            last
        )
        call.enqueue(object : Callback<ArrayList<GithubEntity>> {
            override fun onResponse(call: Call<ArrayList<GithubEntity>>, response: Response<ArrayList<GithubEntity>>) {
                if (response.isSuccessful()) {
                    urlCallback.onSuccess(response.body())
                } else {
                    urlCallback.onFailed(response.code(), "error", response.message().toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubEntity>>, t: Throwable) {
                urlCallback.onFailure(t.message)
            }
        })
    }

    fun getGithubSearchList(
        search: String,
        urlCallback: URLCallback<SearchEntity?>
    ) {
        val urlService = URLFactory().retrofit().create(URLService::class.java)
        val call = urlService.getGithubSearchList(
            search
        )
        call.enqueue(object : Callback<SearchEntity> {
            override fun onResponse(call: Call<SearchEntity>, response: Response<SearchEntity>) {
                if (response.isSuccessful()) {
                    urlCallback.onSuccess(response.body())
                } else {
                    urlCallback.onFailed(response.code(), "error", response.message().toString())
                }
            }

            override fun onFailure(call: Call<SearchEntity>, t: Throwable) {
                urlCallback.onFailure(t.message)
            }
        })
    }
}