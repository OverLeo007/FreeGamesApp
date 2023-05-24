package ru.paskal.freegamesapp.ui

import android.view.View


interface ItemInteractListener {

    fun onItemLongClick(view: View, position: Int)

    fun onItemLikeClick(view: View, position: Int)
}