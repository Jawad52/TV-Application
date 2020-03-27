package com.jawad.androidtv.di

import com.jawad.androidtv.ui.fragment.player.MediaPlayerFragment
import com.jawad.androidtv.ui.fragment.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The class FragmentModuleBuilder
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */
@Suppress("unused")
@Module
abstract class FragmentModuleBuilder {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment
    @ContributesAndroidInjector
    abstract fun contributeMediaPlayerFragment(): MediaPlayerFragment
}