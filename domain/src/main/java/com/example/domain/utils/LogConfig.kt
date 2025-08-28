package com.example.domain.utils

import android.util.Log
import com.example.domain.resources.StringResources.APP_TAG

@Suppress("unused")
fun Any.d(appTag: String = APP_TAG) {
    Log.d(appTag, this.toString())
}

@Suppress("unused")
fun Any.e(appTag: String = APP_TAG) {
    Log.e(appTag, this.toString())
}