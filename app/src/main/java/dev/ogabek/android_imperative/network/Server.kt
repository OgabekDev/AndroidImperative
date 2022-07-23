package dev.ogabek.android_imperative.network

object Server {

    const val IS_TESTER = true

    init {
        System.loadLibrary("keys")
    }

    external fun getDevelopment(): String
    external fun getProduction(): String


}