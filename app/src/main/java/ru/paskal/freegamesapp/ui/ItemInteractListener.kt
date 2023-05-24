package ru.paskal.freegamesapp.ui

import android.view.View

/**
 * ItemInteractListener - интерфейс слушателя взаимодействия с элементами.
 * Он определяет методы для обработки долгого нажатия на элемент и нажатия на кнопку "Лайк".
 */
interface ItemInteractListener {
    /**
     * Вызывается при долгом нажатии на элемент.
     *
     * @param view View, на которую было совершено долгое нажатие.
     * @param position Позиция элемента в списке.
     */
    fun onItemLongClick(view: View, position: Int)

    /**
     * Вызывается при нажатии на кнопку "Лайк".
     *
     * @param view View, на которую было совершено нажатие.
     * @param position Позиция элемента в списке.
     */
    fun onItemLikeClick(view: View, position: Int)
}