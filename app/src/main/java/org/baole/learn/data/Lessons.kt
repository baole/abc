package org.baole.learn.data

data class LessonInfo(val name: String, val json: String)

data class Lessons(val lessons: List<LessonInfo>) {
    var selectedLesson = 0
}

data class LessonPart(val item: String, val prono: String, val pictureUrl: String?, val example: String)

data class LessonDetail(val parts: List<LessonPart>)