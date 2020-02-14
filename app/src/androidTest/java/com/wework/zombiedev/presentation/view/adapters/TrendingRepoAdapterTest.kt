package com.wework.zombiedev.presentation.view.adapters


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.wework.zombiedev.R
import com.wework.zombiedev.ui.main.TrendingRepoActivity


import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TrendingRepoAdapterTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<TrendingRepoActivity> = ActivityTestRule(
        TrendingRepoActivity::class.java
    )

    @Before
    fun setUp() {
        //pause for faking latency
        Thread.sleep(5000)
    }




}