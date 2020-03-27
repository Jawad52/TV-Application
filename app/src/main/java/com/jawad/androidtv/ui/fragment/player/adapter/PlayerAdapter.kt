package com.jawad.androidtv.ui.fragment.player.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jawad.androidtv.R
import com.jawad.androidtv.data.remote.dto.Player

/**
 * The class PlayerAdapter
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

class PlayerAdapter : ListAdapter<Player, PlayerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldPlayer: Player, newPlayer: Player): Boolean {
        return oldPlayer.id == newPlayer.id
    }

    override fun areContentsTheSame(oldPlayer: Player, newPlayer: Player): Boolean {
        return oldPlayer == newPlayer
    }
}