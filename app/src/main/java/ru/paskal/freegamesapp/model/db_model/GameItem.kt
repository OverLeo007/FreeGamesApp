package ru.paskal.freegamesapp.model.db_model

import android.text.Spanned
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_games")
data class GameItem(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "imgUrl")
    var imgUrl: String?,


    @ColumnInfo(name = "readMoreUrl")
    var readMoreUrl: String?,
)