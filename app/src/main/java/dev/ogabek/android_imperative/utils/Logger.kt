package dev.ogabek.android_imperative.utils

import android.util.Log
import dev.ogabek.android_imperative.network.Server

object Logger {

    fun d(tag: String, msg: String) {
        if (Server.IS_TESTER) Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (Server.IS_TESTER) Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (Server.IS_TESTER) Log.v(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (Server.IS_TESTER) Log.e(tag, msg)
    }

}