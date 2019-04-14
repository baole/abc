package org.baole.learn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LessonsViewModel() : ViewModel() {
    var liveData: LiveData<Lessons> = LessonsRepository.instance.getLessons()

}