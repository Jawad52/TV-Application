package com.jawad.androidtv.ui.fragment.player

import androidx.fragment.app.Fragment
import android.view.View
import com.jawad.androidtv.R
import com.jawad.androidtv.di.ViewModelFactory
import com.jawad.androidtv.di.injectViewModel
import com.jawad.androidtv.ui.base.BaseFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MediaPlayerFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: ViewModelFactory

    private lateinit var mediaPlayerFragment: MediaPlayerViewModel

    override val layoutId: Int
        get() = R.layout.fragment_media_player

    override fun initializeDagger() {
        mediaPlayerFragment = injectViewModel(viewModel)
    }

    override fun initializePresenter(view: View) {

    }
}
