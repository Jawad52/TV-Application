package com.jawad.androidtv.ui.fragment.player

import com.jawad.androidtv.data.DataRepository
import com.jawad.androidtv.di.CoroutineScropeIO
import com.jawad.androidtv.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The class MediaPlayerViewModel
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */

class MediaPlayerViewModel @Inject constructor(
    @CoroutineScropeIO private val coroutineScrope: CoroutineScope,
    private var repository: DataRepository
) : BaseViewModel() {

    val homeData by lazy {
        repository.getHomeDataList(coroutineScrope)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScrope.cancel()
    }
}