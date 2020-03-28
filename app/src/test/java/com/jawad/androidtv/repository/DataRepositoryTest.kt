package com.jawad.androidtv.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jawad.androidtv.data.DataRepository
import com.jawad.androidtv.data.remote.Lineups
import com.jawad.androidtv.data.remote.MediaService
import com.jawad.androidtv.data.remote.Result
import com.jawad.androidtv.data.remote.ResultsResponse
import com.jawad.androidtv.data.remote.dataSource.RemoteDataSource
import com.jawad.androidtv.data.remote.dto.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.AnyOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.internal.matchers.Any
import retrofit2.Response

/**
 * The class DataRepositoryTEst
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

@RunWith(JUnit4::class)
class DataRepositoryTest {
    private lateinit var repository: DataRepository
    private val service = mock(MediaService::class.java)
    private val remoteDataSource = RemoteDataSource(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Mock
    private lateinit var liveData: Observer<Result<Data>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = DataRepository(remoteDataSource).apply {
            getLineupsData(coroutineScope).observeForever(liveData)
        }
    }

    @Test
    fun `load LineupsData From check for success loading state`() {
        runBlocking {
            //When
            repository.getLineupsData(coroutineScope)
            //Then
            verify(liveData).onChanged(Result.loading())
        }
    }
}