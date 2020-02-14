package com.wework.zombiedev.ui.main.developers



import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


import com.wework.zombiedev.R
import com.wework.zombiedev.common.utils.hide
import com.wework.zombiedev.common.utils.show
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity


class TrendingDevAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val trendingDevList = mutableListOf<TrendingDevEntity>()

    fun updateData(list : List<TrendingDevEntity>){
        trendingDevList.clear()
        trendingDevList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_dev_item, parent, false)
        return TrendingDevViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trendingDevList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TrendingDevViewHolder) {
            holder.bind(trendingDevList[position])
        }
    }







    class TrendingDevViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {


        val userNameTv by lazy { itemView.findViewById<TextView>(R.id.username_tv) }
        val nameTv by lazy { itemView.findViewById<TextView>(R.id.name_tv) }
        val avatarIv by lazy { itemView.findViewById<ImageView>(R.id.avatar_iv) }
        val urlTv by lazy { itemView.findViewById<TextView>(R.id.url_tv) }




        @SuppressLint("CheckResult")
        fun bind(devItem : TrendingDevEntity){


            userNameTv.text = devItem.username
            nameTv.text = devItem.name
            Glide.with(avatarIv).load(devItem.avatar)
                .apply(RequestOptions.circleCropTransform().override((32 * Resources.getSystem().displayMetrics.density).toInt()))
                .transition(DrawableTransitionOptions().crossFade())
                .into(avatarIv)
            urlTv.text = devItem.url
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }


}