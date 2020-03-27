package com.jawad.androidtv

import android.app.Activity
import android.app.Application
import com.jawad.androidtv.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * The class App
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        app = WeakReference(this)
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    companion object {
        private lateinit var app: WeakReference<App>
        fun getInstance(): App? {
            return app.get()
        }
    }
}