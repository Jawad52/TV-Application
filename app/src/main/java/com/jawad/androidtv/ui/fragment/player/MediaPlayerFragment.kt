package com.jawad.androidtv.ui.fragment.player

import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.jawad.androidtv.R
import com.jawad.androidtv.data.remote.Result
import com.jawad.androidtv.di.ViewModelFactory
import com.jawad.androidtv.di.injectViewModel
import com.jawad.androidtv.ui.RxBus
import com.jawad.androidtv.ui.base.BaseFragment
import com.jawad.androidtv.ui.base.listeners.PlayerListener
import com.jawad.androidtv.ui.fragment.player.adapter.PlayerAdapter
import com.jawad.androidtv.ui.fragment.player.adapter.VerticalItemDecoration
import com.jawad.androidtv.ui.fragment.player.mediaplayer.MediaPlayer
import com.mindvalley.channels.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_media_player.view.*
import kotlinx.android.synthetic.main.view_menu.view.*
import kotlinx.android.synthetic.main.view_overlay.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MediaPlayerFragment : BaseFragment(), PlayerListener {

    @Inject
    lateinit var viewModel: ViewModelFactory

    private lateinit var mediaPlayerFragment: MediaPlayerViewModel
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar
    private lateinit var messageTextView: TextView
    private lateinit var menuButton: Button
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var overlayView: View

    override val layoutId: Int
        get() = R.layout.fragment_media_player

    override fun initializeDagger() {
        mediaPlayerFragment = injectViewModel(viewModel)
    }

    /**
     * Initialize Presenter methods
     *
     *  Call initialize all and set view to observer on data change in the view model
     *  Passing view from BaseFragment in onCreateView function
     * @param view
     */
    override fun initializePresenter(view: View) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setPlayerListener(this)
        playerView = view.player_view
        messageTextView = view.tv_error_message
        progressBar = view.pb_player
        overlayView = view.in_overlay
        menuButton = view.ic_menu.bt_menu
        /*menu button to perform set visibility of the overlay view*/
        view.ic_menu.bt_exit.setOnClickListener {
            activity!!.finish()
        }
        /*exit button to perform close application*/
        overlayView.view_center.setOnClickListener {
            overlayView.visibility = View.GONE
            menuButton.requestFocus()
        }

        /*Sets the recycler view spacing between items*/
        overlayView.rv_home_team.setHasFixedSize(true)
        overlayView.rv_home_team.addItemDecoration(
            VerticalItemDecoration(resources.getDimension(R.dimen._10sdp).toInt(), true)
        )
        overlayView.rv_away_team.setHasFixedSize(true)
        overlayView.rv_away_team.addItemDecoration(
            VerticalItemDecoration(resources.getDimension(R.dimen._10sdp).toInt(), true)
        )
        /*Key event listener from the remote device*/
        RxBus.listen(Int::class.java).subscribe {
            onKeyDown(it)
        }

        subscribeUi(view)
    }

    /**
     * KeyEvents from the remote to perform action
     * Check if the keypress corresponds to a menuitem's
     * perform the action.
     * @param keyCode Code from remote
     */
    private fun onKeyDown(keyCode: Int) {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {//Clear overlay if visible else close the application
                if (overlayView.visibility == View.VISIBLE) View.GONE
                else
                    activity?.finish()
            }
            KeyEvent.KEYCODE_MENU -> {
                menuButton.performClick()
            }
        }
    }

    /**
     * Observer all list to update UI on data change. If MutableLiveData already has data
     * set, it will be delivered to the observer.
     * When data changes views will receive the last available data from the server and
     * local database.
     */
    private fun subscribeUi(view: View) {
        mediaPlayerFragment.homeData.observe(this, Observer {
            when (it.status) {
                Result.Status.LOADING -> {
                }
                Result.Status.SUCCESS -> {
                    it.data?.homeTeam!!.players.let { homeList ->
                        val homeAdapter = PlayerAdapter()
                        view.in_overlay.rv_home_team.adapter = homeAdapter
                        homeAdapter.submitList(homeList?.sortedBy { homeItem -> homeItem?.jerseyNumber })
                    }
                    it.data.awayTeam!!.players.let { awayList ->
                        val awayAdapter = PlayerAdapter()
                        view.in_overlay.rv_away_team.adapter = awayAdapter
                        awayAdapter.submitList(awayList?.sortedBy { awayItem -> awayItem?.jerseyNumber })
                    }
                    /*Enabling on click for menu item*/
                    menuButton.setOnClickListener {
                        overlayView.visibility =
                            if (overlayView.visibility != View.VISIBLE) View.VISIBLE else View.GONE
                        view.in_overlay.rv_home_team.requestFocus()
                    }
                    EspressoIdlingResource.decrement()
                }
                Result.Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    EspressoIdlingResource.decrement()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer.onStart()
        playerView.player = mediaPlayer.getSimpleExoPlayer()
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer.getSimpleExoPlayer() == null) {
            mediaPlayer.onResume()
            playerView.player = mediaPlayer.getSimpleExoPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.onStop()
        playerView.player = null
    }

    /**
     * Any data loading error will be displayed
     * @param message to error message
     */
    override fun onPlayerError(message: String) {
        if (message.isNotEmpty()) {
            progressBar.visibility = View.GONE
            messageTextView.visibility = View.VISIBLE
            messageTextView.text = message
        } else {
            messageTextView.visibility = View.GONE
            messageTextView.text = ""
        }
    }

    /**
     * Handling view while Player State changed
     * 1) Progress Bar visibility set based on the player status
     * 2) Screen will be Keep on while playing content
     * @param playbackState for the play state
     */
    override fun onPlayerStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_BUFFERING -> {
                progressBar.visibility = View.VISIBLE
                playerView.keepScreenOn = true
            }
            Player.STATE_READY -> {
                progressBar.visibility = View.GONE
                playerView.keepScreenOn = true
            }
            Player.STATE_ENDED, Player.STATE_IDLE -> {
                playerView.keepScreenOn = false
            }
        }
    }
}
