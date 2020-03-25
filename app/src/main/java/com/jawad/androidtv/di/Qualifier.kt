package com.jawad.androidtv.di

import javax.inject.Qualifier

/**
 * The class Qualifier
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MediaAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO