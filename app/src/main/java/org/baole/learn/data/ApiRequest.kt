package org.baole.learn.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiRequest {
    @GET("/lessons.json")
    fun getLessons(): Call<Lessons>

    @GET("/{name}.json")
    fun getLesson(@Path("name") json: String): Call<LessonDetail>
}