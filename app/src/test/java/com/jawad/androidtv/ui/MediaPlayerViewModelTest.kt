package com.jawad.androidtv.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jawad.androidtv.data.DataRepository
import com.jawad.androidtv.ui.fragment.player.MediaPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

/**
 * The class MediaPlayerViewModelTest
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

@RunWith(JUnit4::class)
class MediaPlayerViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = Mockito.mock(DataRepository::class.java)

    @Test
    fun `fetchData from DataRepository`() {
        verify(repository, Mockito.never()).getLineupsData(coroutineScope)
    }
}