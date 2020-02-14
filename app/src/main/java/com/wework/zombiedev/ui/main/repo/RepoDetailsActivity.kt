package com.wework.zombiedev.ui.main.repo


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wework.zombiedev.R
import com.wework.zombiedev.common.values.Constants.AUTHOR_NAME
import com.wework.zombiedev.common.values.Constants.FORK_COUNT
import com.wework.zombiedev.common.values.Constants.REPO_NAME
import com.wework.zombiedev.common.values.Constants.REPO_DESC
import com.wework.zombiedev.common.values.Constants.STAR_COUNT


import com.wework.zombiedev.ui.base.BaseActivity

import kotlinx.android.synthetic.main.repo_details_layout.*


class RepoDetailsActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repo_details_layout)
        setValues()
        setClickListeners()
    }


    companion object {
        fun startActivity(ctx : Context, authorName :String, starsCount : String,
                           repoUrl :String, forkCount : String, repoName : String ){
            val intent = Intent(ctx,RepoDetailsActivity::class.java).apply {
                putExtra(STAR_COUNT,starsCount)
                putExtra(AUTHOR_NAME,authorName)
                putExtra(REPO_NAME,repoName)
                putExtra(REPO_DESC,repoUrl)
                putExtra(FORK_COUNT,forkCount)
            }
            ctx.startActivity(intent)
        }


    }

    private fun setValues() {
        author_tv.text = intent?.getStringExtra(AUTHOR_NAME)
        repo_name_tv.text = intent?.getStringExtra(REPO_NAME)
        star_tv.text = intent?.getStringExtra(STAR_COUNT)
        fork_tv.text = intent?.getStringExtra(FORK_COUNT)
        author_tv.text = intent?.getStringExtra(AUTHOR_NAME)
        desc_tv.text = intent?.getStringExtra(REPO_DESC)

    }

    private fun setClickListeners(){
        back_iv.setOnClickListener { this.onBackPressed() }
    }

}
