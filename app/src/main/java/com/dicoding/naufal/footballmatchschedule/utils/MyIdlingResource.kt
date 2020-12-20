package com.dicoding.naufal.footballmatchschedule.utils

import androidx.test.espresso.IdlingResource

open class MyIdlingResource(private val mResourceName: String) : androidx.test.espresso.IdlingResource {
    private var idle = true

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return mResourceName
    }

    override fun isIdleNow(): Boolean = idle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        resourceCallback = callback
    }

    fun begin() {
        idle = false
    }

    fun done() {
        idle = true
        resourceCallback?.onTransitionToIdle()
    }
}

