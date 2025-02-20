package com.vk.domain.usecase

import android.util.Log
import com.vk.common.domain.BaseUseCase
import com.vk.domain.model.Video
import com.vk.domain.repository.PlaylistRepository
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<List<Video>, GetPlaylistUseCase.Params>() {

    override suspend fun execute(params: Params): List<Video> {
        val list = playlistRepository.getPlaylist()
        Log.println(Log.DEBUG, "UUUU", list.toString())
        return playlistRepository.getPlaylist()
    }

    class Params
}