package ru.paskal.freegamesapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.paskal.freegamesapp.model.db_model.DbAccessable
import ru.paskal.freegamesapp.model.db_model.DatabaseModel

/**
 * ViewModelFactory - фабрика ViewModel, расширяющая AndroidViewModelFactory.
 * Она используется для создания экземпляров ViewModel с доступом к базе данных.
 *
 * @param application приложение, на основе которого создается ViewModel.
 * @param dbModel подель базы данных, которая будет использоваться в ViewModel.
 */
class ViewModelFactory(
    private val application: Application,
    private val dbModel: DatabaseModel
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    /**
     * Создает экземпляр ViewModel на основе указанного класса модели.
     *
     * @param modelClass Класс модели ViewModel, который необходимо создать.
     * @return Экземпляр ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (DbAccessable::class.java.isAssignableFrom(modelClass)) {
            try {
                val constructor = modelClass.getConstructor(
                    Application::class.java,
                    DatabaseModel::class.java
                )
                constructor.newInstance(application, dbModel)
            } catch (e: NoSuchMethodException) {
                throw IllegalArgumentException(
                    "DbAccessable ViewModel does not have the required constructor: ${e.message}"
                )
            }
        } else
            try {
                val constructor = modelClass.getConstructor(
                    Application::class.java,
                )
                constructor.newInstance(application)
            } catch (e: NoSuchMethodException) {
                throw IllegalArgumentException(
                    "ViewModel does not have the required constructor: ${e.message}"
                )
            }
    }
}