package com.jawad.androidtv.ui.fragment.player.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * The class VerticalItemDecoration
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 27 Mar 2020
 */

class VerticalItemDecoration(private val margin: Int,
                             private val includeEdge: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (includeEdge && parent.getChildAdapterPosition(view) == 0) top = margin
            bottom = margin
        }
    }
}