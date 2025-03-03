package com.vk.domain.usecase

import com.vk.common.domain.BaseUseCase
import com.vk.domain.model.Video
import com.vk.domain.repository.PlaylistRepository
import com.vk.domain.utils.CustomResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<Flow<CustomResult<List<Video>>>, GetPlaylistUseCase.Params>() {

    override suspend fun execute(params: Params): Flow<CustomResult<List<Video>>> {
        return playlistRepository.getPlaylist(params.withSync)
    }

    data class Params(val withSync: Boolean)
}