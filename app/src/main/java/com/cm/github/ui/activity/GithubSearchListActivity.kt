package com.cm.github.ui.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnEditorAction
import com.cm.github.R
import com.cm.github.CMApplication
import com.cm.github.entity.GithubEntity
import com.cm.github.entity.SearchEntity
import com.cm.github.ui.adapter.AccountAdapter
import com.cm.github.url.URLCallback
import com.cm.github.url.URLController
import kotlinx.android.synthetic.main.activity_github_search_list.*

class GithubSearchListActivity : BaseActivity() {

    @OnEditorAction(R.id.etSearch)
    internal fun onEditorAction(i: Int): Boolean {
        if (i == EditorInfo.IME_ACTION_SEND) {
            closeKeyboard()

            initData()
            return true
        }
        return false
    }

    @OnClick(R.id.tvClose)
    fun doClose() {
        finish()
        overridePendingTransition(0, R.anim.fade_out)
    }

    var isLoading = false

    companion object {
        var listAccount : ArrayList<GithubEntity> = ArrayList()
        lateinit var adapter : AccountAdapter
        lateinit var entity : GithubEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_search_list)

        ButterKnife.bind(this)
    }

    fun initData() {
        showLoading(this@GithubSearchListActivity)
        isLoading = true

        URLController().getGithubSearchList(etSearch.text.toString(), object : URLCallback<SearchEntity?>() {
            override fun onSuccess(`object`: SearchEntity?) {
                runOnUiThread {
                    val search = `object`
                    if (search != null) {
                        listAccount.clear()
                        listAccount.addAll(search.items)

                        initAdapter()
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

                    showMessage(title, message, this@GithubSearchListActivity)
                }
            }

            override fun onFailure(message: String?) {
                runOnUiThread {
                    isLoading = false
                    closeLoading()
                    setupNotFound()

                    showErrorInternet(this@GithubSearchListActivity)
                }
            }
        })
    }

    fun initAdapter() {
        if (listAccount.size > 0) {
            adapter = AccountAdapter(this@GithubSearchListActivity, listAccount)

            val layoutManager = LinearLayoutManager(CMApplication().getAppContext())
            rvAccount.setLayoutManager(layoutManager)
            rvAccount.setItemAnimator(DefaultItemAnimator())
            rvAccount.setAdapter(adapter)
            rvAccount.setFocusable(false)

            rlNoData.visibility = View.GONE
            rvAccount.visibility = View.VISIBLE
        } else {
            setupNotFound()
        }
    }

    fun setupNotFound() {
        rlNoData.visibility = View.VISIBLE
        rvAccount.visibility = View.GONE
    }

}