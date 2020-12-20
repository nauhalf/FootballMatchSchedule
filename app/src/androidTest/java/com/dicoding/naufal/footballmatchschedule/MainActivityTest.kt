package com.dicoding.naufal.footballmatchschedule

import com.google.android.material.tabs.TabLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.naufal.footballmatchschedule.R.id.*
import com.dicoding.naufal.footballmatchschedule.mvp.main.MainActivity
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    private fun <T> result(matcher: Matcher<T>, i: Int): Matcher<T> {
        return object : BaseMatcher<T>() {
            private var resultIndex = -1
            override fun matches(item: Any): Boolean {
                if (matcher.matches(item)) {
                    resultIndex++
                    return resultIndex == i
                }
                return false
            }

            override fun describeTo(description: Description) {}
        }
    }

    fun selectTab(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                        ?: throw PerformException.Builder()
                                .withCause(Throwable("No tab at index $tabIndex"))
                                .build()

                tabAtIndex.select()
            }
        }
    }

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }


    @Test
    fun testAppBehaviour(){
        var league = "Spanish La Liga"
        onView(ViewMatchers.withText(R.string.upcoming)).check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 0))
                .check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(search_match))
                .perform(click())

        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Barce"))


        onView(withId(recycler_view))
                .check(matches(isDisplayed()))

        onView(withId(recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())

        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))

        pressBack()

        pressBack()

        pressBack()

        onView(withId(R.id.tab_layout)).perform(selectTab(1))

        Thread.sleep(1000)

        onView(result(withId(spinner_league), 1))
                .check(matches(isDisplayed()))

        onView(result(withId(spinner_league), 1)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(league))).perform(click())
        onView(result(withId(spinner_league), 1)).check(matches(withSpinnerText(containsString(league))))

        onView(result(withId(recycler_view), 1))
                .check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 1))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))

        onView(result(withId(recycler_view), 1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))

        pressBack()

        onView(withId(teams_menu)).perform(click())


        //TEAM
        onView(withId(recycler_view))
                .check(matches(isDisplayed()))
        onView(withId(recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))

        pressBack()

        onView(withId(search_team))
                .perform(click())

        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Barce"))

        onView(withId(recycler_view))
                .check(matches(isDisplayed()))

        onView(withId(recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())

        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))

        pressBack()

        onView(withId(androidx.appcompat.appcompat.R.id.search_close_btn)).perform(click())

        onView(withId(androidx.appcompat.appcompat.R.id.search_close_btn)).perform(click())

        onView(result(withId(spinner_league), 0)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(league))).perform(click())
        onView(result(withId(spinner_league), 0)).check(matches(withSpinnerText(containsString(league))))

        onView(result(withId(recycler_view), 0))
                .check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(9,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorites_menu)).perform(click())


        //FAVORITE
        onView(result(withId(recycler_view), 0))
                .check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Remove from favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(result(withId(swipe), 0)).perform(swipeDown())

        onView(withId(R.id.tab_layout)).perform(selectTab(1))

        Thread.sleep(1000)

        onView(result(withId(recycler_view), 1))
                .check(matches(isDisplayed()))

        onView(result(withId(recycler_view), 1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,click()))

        onView(withId(R.id.tab_layout)).perform(selectTab(1))

        Thread.sleep(1000)


        onView(withId(recycler_view))
                .check(matches(isDisplayed()))

        onView(withId(recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))

        Thread.sleep(1000)

        pressBack()

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Remove from favorite"))

        pressBack()

        onView(result(withId(swipe), 1)).perform(swipeDown())
    }


}