package com.jawad.androidtv.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jawad.androidtv.data.remote.MediaService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The class MediaServiceTest
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */
@RunWith(JUnit4::class)
class MediaServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MediaService
    private lateinit var mockWebServer: MockWebServer

    private val itemSize = 20

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediaService::class.java)
    }

    // test the data for api fail result
    @Test
    fun `should fail API request when fetchRemoteData returns with failure result`() {
        runBlocking {
            enqueueResponse("testData.json")
            val resultResponse = service.fetchRemoteData().body()

            val request = mockWebServer.takeRequest()
            Assert.assertNotNull(resultResponse)
            Assert.assertThat(request.path, `is`(not("/sample1.json")))
        }
    }

    // test the data for api pass result
    @Test
    fun `should pass API request when fetchRemoteData returns with success result`() {
        runBlocking {
            enqueueResponse("testData.json")
            val resultResponse = service.fetchRemoteData().body()

            val request = mockWebServer.takeRequest()
            Assert.assertNotNull(resultResponse)
            Assert.assertThat(request.path, `is`("/sample.json"))
        }
    }

    //test number items in home team and away team is equals 20
    @Test
    fun `get fetchRemoteData success returns with success with proper data size`() {
        runBlocking {
            enqueueResponse("testData.json")
            val resultResponse = service.fetchRemoteData().body()
            val homeTeamModel = resultResponse!!.lineups.data.homeTeam!!.players
            val awayTeamModel = resultResponse.lineups.data.awayTeam!!.players

            Assert.assertThat(homeTeamModel!!.size, `is`(itemSize))
            Assert.assertThat(awayTeamModel!!.size, `is`(itemSize))
        }
    }

    // test value in home team item data list
    @Test
    fun `should success when fetchRemoteData returns success with proper homeTeam data`() {
        runBlocking {
            enqueueResponse("testData.json")
            val resultResponse = service.fetchRemoteData().body()
            val homeTeamModel = resultResponse!!.lineups.data.homeTeam!!.players
            val player = homeTeamModel?.get(0)
            Assert.assertThat(player?.id, `is`(28491))
            Assert.assertThat(player?.isCaptain, `is`(false))
            Assert.assertThat(player?.jerseyNumber, `is`("26"))
            Assert.assertThat(player?.name, `is`("علي الحبسي"))
            Assert.assertThat(player?.order, `is`(0))
            Assert.assertThat(player?.role, `is`("حارس مرمى"))
            Assert.assertThat(player?.startInField, `is`(true))
            Assert.assertThat(player?.xCoordinate, `is`(500))
            Assert.assertThat(player?.yCoordinate, `is`(120))
        }
    }

    // test value in away team item data list
    @Test
    fun `should success when fetchRemoteData returns success with proper awayTeam data`() {
        runBlocking {
            enqueueResponse("testData.json")
            val resultResponse = service.fetchRemoteData().body()
            val awayTeamModel = resultResponse!!.lineups.data.awayTeam!!.players
            val player = awayTeamModel?.get(1)
            Assert.assertThat(player?.id, `is`(109877))
            Assert.assertThat(player?.isCaptain, `is`(false))
            Assert.assertThat(player?.jerseyNumber, `is`("88"))
            Assert.assertThat(player?.name, `is`("ماجد هزازي"))
            Assert.assertThat(player?.order, `is`(1))
            Assert.assertThat(player?.role, `is`("مدافع"))
            Assert.assertThat(player?.startInField, `is`(true))
            Assert.assertThat(player?.xCoordinate, `is`(130))
            Assert.assertThat(player?.yCoordinate, `is`(380))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                source.readString(Charsets.UTF_8)
            )
        )
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}