package com.vk.data.player.datasource.remote.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Protocol
import java.io.BufferedReader
import java.io.InputStreamReader

class AssetInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val inputStream = context.assets.open("videos.json")
        val jsonString = BufferedReader(InputStreamReader(inputStream)).use { it.readText() }

        return Response.Builder()
            .request(chain.request())
            .protocol(chain.connection()?.protocol() ?: Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(null, jsonString))
            .build()
    }
}