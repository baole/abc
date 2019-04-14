package org.baole.learn.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class LessonDetailsViewModel() : ViewModel() {
    var liveData: LiveData<LessonDetail> = MutableLiveData()

    fun loadNext(nextlesson : String) {
        liveData = LessonsRepository.instance.getLessonDetails(nextlesson)

    }
}
