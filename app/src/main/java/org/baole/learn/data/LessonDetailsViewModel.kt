package org.baole.learn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.baole.learn.repository.LessonsRepository


class LessonDetailsViewModel() : ViewModel() {
    var liveData: LiveData<LessonDetail> = MutableLiveData()

    fun loadNext(nextlesson : String) {
        liveData = LessonsRepository.instance.getLessonDetails(nextlesson)

    }
}
