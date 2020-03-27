package com.jawad.androidtv

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jawad.androidtv.ui.MainActivity
import kotlinx.android.synthetic.main.view_overlay.*
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The class MediaPlayerFragmentTest
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class MediaPlayerFragmentTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setUp() {
        mIdlingResource = mActivityTestRule.activity.countingIdlingResource
        IdlingRegistry.getInstance().register(mIdlingResource)
    }

    /*initial test
      visibility test for all main views in media player page*/
    @Test
    fun initialTest() {
        Espresso.onView(ViewMatchers.withId(R.id.bt_exit)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.player_view)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.pb_player)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tv_error_message)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    //menu button test to show overlay view
    @Test
    fun testToShowOverlayView() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        /*Click on menu button to show overlay*/
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    //menu button test to hide overlay view
    @Test
    fun testToHideOverlayView() {
        /*Click on menu button to show overlay*/
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        /*Click on menu button to hide overlay*/
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    //home data list test scroll test
    @Test
    fun testScrollForHomeList() {
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_home_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        for (i in 0 until mActivityTestRule.activity.rv_home_team.adapter!!.itemCount - 1)
            Espresso.onView(ViewMatchers.withId(R.id.rv_home_team))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        i,
                        ViewActions.scrollTo()
                    )
                )
    }

    //away data list test
    @Test
    fun testScrollForAwayList() {
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_away_team)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        for (i in 0 until mActivityTestRule.activity.rv_away_team.adapter!!.itemCount - 1)
            Espresso.onView(ViewMatchers.withId(R.id.rv_away_team))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        i,
                        ViewActions.scrollTo()
                    )
                )
    }

    //center view click hide view test
    @Test
    fun testBackPressToHideOverlay() {
        Espresso.onView(ViewMatchers.withId(R.id.bt_menu)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.in_overlay)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.view_center)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.in_overlay)).check(
            ViewAssertions.matches(
                Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }
}