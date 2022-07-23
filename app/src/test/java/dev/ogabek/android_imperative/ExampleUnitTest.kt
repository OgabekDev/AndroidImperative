package dev.ogabek.android_imperative

import dev.ogabek.android_imperative.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun checkPopularTVShowsApi() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(), 200)
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
        assertEquals(response.body()!!.tv_shows.size, 20)
        assertTrue(response.body()!!.tv_shows[0].status == "Running")
    }

    @Test
    fun checkDetailsTVShowsApi() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        assertEquals(response.code(), 200)
    }

}