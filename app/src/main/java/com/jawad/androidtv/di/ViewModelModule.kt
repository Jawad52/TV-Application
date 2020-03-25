package com.jawad.androidtv.di

import androidx.lifecycle.ViewModel
import com.jawad.androidtv.ui.components.fragment.mediaplayer.MediaPlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * The class ViewModelModule
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MediaPlayerViewModel::class)
    abstract fun mediaPlayerViewModel(viewModel: MediaPlayerViewModel): ViewModel
}