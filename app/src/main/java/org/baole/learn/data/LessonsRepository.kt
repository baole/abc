package org.baole.learn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LessonsRepository {

    companion object {
        val instance = LessonsRepository()
            get
    }

    private val apiService: ApiRequest = RetrofitRequest.createApiRequest()

    fun getLessons(): LiveData<Lessons> {
        val data = MutableLiveData<Lessons>()
        apiService.getLessons().enqueue(object : Callback<Lessons> {
            override fun onResponse(call: Call<Lessons>, response: Response<Lessons>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<Lessons>, t: Throwable) {
                data.value = null

            }
        })
        return data
    }

    fun getLessonDetails(json: String): LiveData<LessonDetail> {
        val data = MutableLiveData<LessonDetail>()
        apiService.getLesson(json).enqueue(object : Callback<LessonDetail> {
            override fun onResponse(call: Call<LessonDetail>, response: Response<LessonDetail>) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                } else {
                    data.postValue(null)
                }
            }

            override fun onFailure(call: Call<LessonDetail>, t: Throwable) {
                data.postValue(null)

            }
        })
        return data
    }

}