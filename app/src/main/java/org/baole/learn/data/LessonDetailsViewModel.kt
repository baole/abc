package org.baole.learn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LessonDetailsViewModel(val lessonId: String) : ViewModel() {
    var liveData: LiveData<LessonDetail>

    init {
        liveData = LessonsRepository.instance.getLessonDetails(lessonId)
    }
}