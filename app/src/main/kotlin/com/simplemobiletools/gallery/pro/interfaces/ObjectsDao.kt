package com.simplemobiletools.gallery.pro.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.simplemobiletools.gallery.pro.models.Medium
import com.simplemobiletools.gallery.pro.models.RecognizableObject

@Dao
interface ObjectsDao {
    @Insert(onConflict = REPLACE)
    fun insert(object_: RecognizableObject)

    @Insert(onConflict = REPLACE)
    fun insertAll(objects: List<RecognizableObject>)

    @Delete
    fun deleteObject(vararg object_: RecognizableObject)
}
