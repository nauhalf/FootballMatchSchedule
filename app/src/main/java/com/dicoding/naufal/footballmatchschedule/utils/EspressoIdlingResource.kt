package com.dicoding.naufal.footballmatchschedule.utils

import androidx.test.espresso.IdlingResource

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = MyIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun begin() {
        mCountingIdlingResource.begin()
    }

    fun done() {
        mCountingIdlingResource.done()
    }
}
