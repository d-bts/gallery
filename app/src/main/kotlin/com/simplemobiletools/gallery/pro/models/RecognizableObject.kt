package com.simplemobiletools.gallery.pro.models

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "objects",
    foreignKeys = [
        ForeignKey(entity = Medium::class, parentColumns = ["id"], childColumns = ["medium_id"]),
        ForeignKey(entity = Recognizable::class, parentColumns = ["id"], childColumns = ["recognizable_id"])
    ]
)
data class RecognizableObject(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "medium_id") var medium: Long?,
    @ColumnInfo(name = "recognizable_id") var recognizable: Long?,
    @ColumnInfo(name = "x_position") var x_pos: Int,
    @ColumnInfo(name = "y_position") var y_pos: Int,
    @ColumnInfo(name = "width") var width: Int,
    @ColumnInfo(name = "height") var height: Int,
    ) : Serializable {
}
