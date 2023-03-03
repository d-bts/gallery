package com.simplemobiletools.gallery.pro.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recognizables")
data class Recognizable(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "recognizable_count") var recCnt: Int,
) : Serializable {
}
