package com.jawad.androidtv.ui.fragment

import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import com.jawad.androidtv.R
import com.jawad.androidtv.ui.base.BaseFragment
import com.jawad.androidtv.util.Constants

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_splash


    override fun initializeDagger() {
    }

    override fun initializePresenter(view: View) {
        navigateToHomeScreen(view)
    }

    /**
     * Navigates to Media Player Screen after three sec's
     */
    private fun navigateToHomeScreen(view: View) {
        Handler().postDelayed({
            val action =
                SplashFragmentDirections.actionSplashFragmentToMediaPlayerFragment()
            view.findNavController().navigate(action)
        }, Constants.SPLASH_DELAY.toLong())
    }
}
