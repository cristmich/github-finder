package com.cm.github.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cm.github.R
import com.cm.github.entity.GithubEntity
import kotlinx.android.synthetic.main.adapter_account.view.*

class AccountAdapter (mContext: Context, list: List<GithubEntity>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    var context: Context = mContext
    var moreList: List<GithubEntity> = list

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_account, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return moreList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = moreList[position]
        holder.tvName.text = item.login

        if (item.avatar_url != "") {
            val requestOption = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()

            Glide.with(context)
                .asBitmap()
                .apply(requestOption)
                .load(item.avatar_url)
                .into(holder.ivIcon)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val ivIcon = itemView.ivIcon
    }
}