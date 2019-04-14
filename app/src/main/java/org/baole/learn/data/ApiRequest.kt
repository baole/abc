package org.baole.learn.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiRequest {
    @GET("baole/abc/master/data/lessons.json")
    fun getLessons(): Call<Lessons>

    @GET("baole/abc/master/data/{name}.json")
    fun getLesson(@Path("name") json: String): Call<LessonDetail>
}