package com.wework.zombiedev.presentation.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.wework.zombiedev.R
import com.wework.zombiedev.ui.main.TrendingRepoActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class TrendingRepoActivityTest {


    companion object {
        const val trendingText: String = "Trending"
    }

    @get:Rule
    var mActivityRule: ActivityTestRule<TrendingRepoActivity> = ActivityTestRule(
        TrendingRepoActivity::class.java
    )

    @Test
    fun toolbarTitleTextTest() {
        onView(withId(R.id.title_tv)).check(matches(withText(trendingText)))
    }

    @Test
    fun validateAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.wework.zombiedev.test", appContext.packageName)
    }

    @Test
    fun swipeToRefreshTest() {
        onView(withId(R.id.swipe_rv)).perform(ViewActions.swipeDown())
            .check { view, _ -> assertTrue((view as SwipeRefreshLayout).isRefreshing) }
    }


}