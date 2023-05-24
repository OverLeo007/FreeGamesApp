package ru.paskal.freegamesapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.paskal.freegamesapp.model.db_model.DbAccessable
import ru.paskal.freegamesapp.model.db_model.DatabaseModel

class ViewModelFactory(
    private val application: Application,
    private val dbModel: DatabaseModel
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
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