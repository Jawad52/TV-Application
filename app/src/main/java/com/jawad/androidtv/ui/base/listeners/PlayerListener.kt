package com.jawad.androidtv.ui.base.listeners

/**
 * The class PlayerListener
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 26 Mar 2020
 */

interface PlayerListener {
    fun onPlayerError(message: String)
    fun progressBarVisibility(visibility: Int)
}