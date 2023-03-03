package com.simplemobiletools.gallery.pro.interfaces

import androidx.room.*
import com.simplemobiletools.gallery.pro.models.Medium
import com.simplemobiletools.gallery.pro.models.Recognizable

@Dao
interface RecognizablesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Recognizable: Recognizable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recognizables: List<Recognizable>)

    @Delete
    fun deleteRecognizable(vararg recognizable: Recognizable)

    @Query("SELECT filename, full_path, parent_path, last_modified, date_taken, size, type, video_duration, is_favorite, deleted_ts, media_store_id FROM media WHERE media_store_id NOT IN (SELECT medium_id FROM objects)")
    fun getMediaNotScanned(): List<Medium>
}
