package org.baole.learn.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRequest {
    companion object {
        fun createApiRequest(): ApiRequest {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(ApiRequest::class.java)
        }
    }
}