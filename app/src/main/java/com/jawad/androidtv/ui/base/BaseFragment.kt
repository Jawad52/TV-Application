package com.jawad.androidtv.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jawad.androidtv.ui.base.listeners.BaseView
import dagger.android.support.AndroidSupportInjection

/**
 * The class BaseFragment
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */

abstract class BaseFragment : Fragment(), BaseView {
    abstract val layoutId: Int

    protected abstract fun initializeDagger()

    protected abstract fun initializePresenter(view: View)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(layoutId, container, false)
        initializePresenter(view)
        return view
    }
}