package org.baole.learn.data

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.baole.learn.repository.LessonsRepository



class LessonsViewModel(app: Application) : AndroidViewModel(app) {
    var liveData: MutableLiveData<Lessons> = MutableLiveData<Lessons>()

    val pref = app.getSharedPreferences("lessons", 0)

    companion object {
        val KEY_LAST_LESSON = "last_lesson"
    }

    init {

        LessonsRepository.instance.getLessons().observeForever {
            val lastLesson = pref.getString(KEY_LAST_LESSON, "")
            it.lessons.forEachIndexed { index, lessonInfo ->
                if (lessonInfo.json == lastLesson) {
                    it.selectedLesson = index
                }
            }
            liveData.postValue(it)
        }
    }

    fun saveLastLesson(lesson: LessonInfo) {
        pref.edit {
            putString(KEY_LAST_LESSON, lesson.json)
        }
    }


    class Factory(private val mApplication: Application) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LessonsViewModel(mApplication) as T
        }
    }

}