package com.jawad.androidtv.di

import com.jawad.androidtv.ui.components.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The class ActivityModuleBuilder
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}