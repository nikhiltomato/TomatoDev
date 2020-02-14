package com.wework.zombiedev.ui.main.repo



import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


import com.wework.zombiedev.R
import com.wework.zombiedev.common.utils.hide
import com.wework.zombiedev.common.utils.show
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity


class TrendingRepoAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val trendingRepoList = mutableListOf<TrendingRepoEntity>()

    fun updateData(list : List<TrendingRepoEntity>){
        trendingRepoList.clear()
        trendingRepoList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_repo_item, parent, false)
        return TrendingRepoViewHolder(clickListener,view)
    }

    override fun getItemCount(): Int {
        return trendingRepoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TrendingRepoViewHolder) {
            holder.bind(trendingRepoList[position])
        }
    }




    class TrendingRepoViewHolder(private val clickListener: OnItemClickListener, itemView : View) : RecyclerView.ViewHolder(itemView) {

        val authorTv by lazy { itemView.findViewById<TextView>(R.id.author_tv) }
        val repoNameTv by lazy { itemView.findViewById<TextView>(R.id.repo_name_tv) }
        val descTv by lazy { itemView.findViewById<TextView>(R.id.desc_tv) }
        val avatarIv by lazy { itemView.findViewById<ImageView>(R.id.avatar_iv) }
        val parentLyt by lazy { itemView.findViewById<ConstraintLayout>(R.id.repo_item_parent_lyt) }


        @SuppressLint("CheckResult")
        fun bind(repoItem : TrendingRepoEntity){
            authorTv.text = repoItem.author
            repoNameTv.text = repoItem.name
            descTv.text = repoItem.description
            Glide.with(avatarIv).load(repoItem.avatar)
                .apply(RequestOptions.circleCropTransform().override((32 * Resources.getSystem().displayMetrics.density).toInt()))
                .transition(DrawableTransitionOptions().crossFade())
                .into(avatarIv)
            parentLyt.setOnClickListener { clickListener.onItemClicked(repoItem) }
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(repoEntity: TrendingRepoEntity)
    }


}