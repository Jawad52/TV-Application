package com.jawad.androidtv.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jawad.androidtv.ui.base.listeners.BaseView
import dagger.android.AndroidInjection

/**
 * The class BaseActivity
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */
abstract class BaseActivity : FragmentActivity(), BaseView {

    abstract val layoutId: Int

    protected abstract fun initializeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initializeViewModel()
    }
}