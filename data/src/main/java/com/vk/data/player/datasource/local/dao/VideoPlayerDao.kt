package com.vk.data.player.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vk.data.player.datasource.local.model.VideoItemEntity

@Dao
interface VideoPlayerDao {
    @Insert
    suspend fun saveVideoPlaylist(list: List<VideoItemEntity>)

    @Query("DELETE FROM video_item_table")
    fun clearCache()

    @Query("SELECT * FROM video_item_table")
    fun getVideoPlaylist(): List<VideoItemEntity>
}