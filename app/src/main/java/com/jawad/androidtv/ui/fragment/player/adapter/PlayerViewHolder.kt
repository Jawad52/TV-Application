package com.jawad.androidtv.ui.fragment.player.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jawad.androidtv.data.remote.dto.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.player_item.*

/**
 * The class TeamViewHolder
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

class PlayerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    /**
     * Called when onBindViewHolder is triggered in RecyclerView adapter
     * The new bind will be used to set the values to display items
     * @param newsEntity contains the values to set in UI
     */
    fun bind(item: Player) {
        tv_jersey_number.text = item.jerseyNumber
        tv_name.text = item.name
    }
}