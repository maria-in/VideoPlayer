package com.vk.data.player.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vk.data.player.datasource.local.dao.VideoPlayerDao
import com.vk.data.player.datasource.local.model.VideoItemEntity

class VideoPlayerDatabase internal constructor(private val database: VideoPlayerRoomDatabase) {
    val videoPlayerDao: VideoPlayerDao
        get() = database.videoPlayerDao()
}

@Database(
    entities = [VideoItemEntity::class],
    version = 1,
    exportSchema = false
)

internal abstract class VideoPlayerRoomDatabase : RoomDatabase() {

    abstract fun videoPlayerDao(): VideoPlayerDao
}

fun VideoPlayerDatabase(applicationContext: Context): VideoPlayerDatabase {
    val rateTrackerDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        VideoPlayerRoomDatabase::class.java,
        "video_player_db"
    ).build()
    return VideoPlayerDatabase(rateTrackerDatabase)
}