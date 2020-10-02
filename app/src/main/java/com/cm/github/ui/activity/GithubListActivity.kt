package com.cm.github.ui.activity

import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cm.github.R
import com.cm.github.CMApplication
import com.cm.github.controller.AppController
import com.cm.github.entity.GithubEntity
import com.cm.github.ui.adapter.AccountAdapter
import com.cm.github.url.URLCallback
import com.cm.github.url.URLController
import kotlinx.android.synthetic.main.activity_github_list.*

class GithubListActivity :BaseActivity() {
    @OnClick(R.id.vSearch)
    fun doSearch() {
        AppController().openGithubSearchList(this@GithubListActivity, false)
    }

    var isLoading = false
    var isLastPage = false
    var last_position = 0

    companion object {
        var listAccount : ArrayList<GithubEntity> = ArrayList()
        lateinit var adapter : AccountAdapter
        lateinit var entity : GithubEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_list)

        ButterKnife.bind(this)

        initData()
    }

    fun initData() {
        showLoading(this@GithubListActivity)
        isLoading = true

        URLController().getGithubList(last_position, object : URLCallback<ArrayList<GithubEntity>?>() {
            override fun onSuccess(`object`: ArrayList<GithubEntity>?) {
                runOnUiThread {
                    val account_list = `object`
                    if (account_list != null) {
                        if (account_list.size == 0) {
                            isLastPage = true
                        }

                        if (last_position == 0) {
                            listAccount = account_list

                            initAdapter()
                        } else {
                            listAccount.addAll(account_list)

                            updateAdapter()
                        }

                        last_position = account_list.last().id
                    }

                    isLoading = false
                    closeLoading()
                }
            }

            override fun onFailed(code: Int, title: String, message: String) {
                runOnUiThread {
                    isLoading = false
                    closeLoading()
                    setupNotFound()

                    showMessage(title, message, this@GithubListActivity)
                }
            }

            override fun onFailure(message: String?) {
                runOnUiThread {
                    isLoading = false
                    closeLoading()
                    setupNotFound()

                    showErrorInternet(this@GithubListActivity)
                }
            }
        })
    }

    fun initAdapter() {
        if (listAccount.size > 0) {
            adapter = AccountAdapter(this@GithubListActivity, listAccount)

            val layoutManager = LinearLayoutManager(CMApplication().getAppContext())
            rvAccount.setLayoutManager(layoutManager)
            rvAccount.setItemAnimator(DefaultItemAnimator())
            rvAccount.setAdapter(adapter)
            rvAccount.setFocusable(false)

            val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = layoutManager.getChildCount()
                    val totalItemCount = layoutManager.getItemCount()
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= MifareUltralight.PAGE_SIZE
                        ) {
                            initData()
                        }
                    }
                }
            }
            rvAccount.addOnScrollListener(recyclerViewOnScrollListener)

            rlNoData.visibility = View.GONE
            rvAccount.visibility = View.VISIBLE
        } else {
            setupNotFound()
        }
    }

    fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    fun setupNotFound() {
        rlNoData.visibility = View.VISIBLE
        rvAccount.visibility = View.GONE
    }
}