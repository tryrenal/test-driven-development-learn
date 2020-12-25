package com.renaldysabdo.testdrivendevelopment.data.remote

import com.renaldysabdo.testdrivendevelopment.BuildConfig
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/")
    suspend fun searchImage(
        @Query("key") apiKey : String = BuildConfig.API_KEY,
        @Query("q") imageQuery : String
    ) : Response<ImageResponse>
}