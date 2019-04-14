package org.baole.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.baole.learn.databinding.ActivityMainBinding
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import java.util.*
import androidx.lifecycle.ViewModelProviders
import org.baole.learn.data.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

     val TAG = MainActivity::class.java.simpleName

    private lateinit var binder: ActivityMainBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
        tts.language = Locale("vi")
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.actionSpeak.setOnClickListener {
            speak()
        }

        binder.actionNext.setOnClickListener {
            currentPart++
            loadPart()
        }

        binder.actionBack.setOnClickListener {
            var changeLesson = false
            currentPart--
            if (currentPart < 0) {
                currentPart = 0
                if (currentLesson > 0) {
                    currentLesson--
                    changeLesson = true
                }
            }

            if (changeLesson) {
                loadLessonDetails(true)
            } else {
                loadPart()
            }
        }


        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    lateinit var detailViewModel: LessonDetailsViewModel

    fun loadData() {
        val viewModel = ViewModelProviders.of(this).get(LessonsViewModel::class.java)
        viewModel.liveData.observe(this, Observer<Lessons> { t ->
            t?.let {
                onLessonsLoaded(it)
            } ?: Log.e(TAG, "Error while loading lessons")
        })

        detailViewModel = ViewModelProviders.of(this).get(LessonDetailsViewModel::class.java)

    }

    private fun onLessonsLoaded(lessons: Lessons) {
        lessons.lessons.forEach {
            Log.d(TAG,"data: ${it.name}/${it.json}")
        }
        this.lessons = lessons
        this.currentLesson = 0
        loadLessonDetails()
    }

    private fun loadLessonDetails(selectEnd: Boolean = false) {
        this.lessons.lessons.getOrNull(this.currentLesson)?.let {
            detailViewModel.loadNext(it.json)
            detailViewModel.liveData.observe(this, Observer<LessonDetail> { t ->
                t?.let {
                    onLessonDetailsLoaded(it, selectEnd)
                } ?: Log.e(TAG, "Error while loading lessons")
            })
        } ?: Log.e(TAG, "Error while loading lessons $currentLesson")
    }

    lateinit var lessonDetails: LessonDetail
    var currentPart = 0
    var currentLesson = 0
    var currentItem: LessonPart?=null
    lateinit var lessons: Lessons
    private fun onLessonDetailsLoaded(details: LessonDetail, selectEnd: Boolean = false){
        this.lessonDetails=details
        this.currentPart = if (selectEnd) details.parts.size - 1 else 0
        details.parts.forEach {
            Log.d(TAG,"data: ${it.item}/${it.prono}")
        }

        loadPart()
    }

    fun loadPart(){
        if(!::lessonDetails.isInitialized) return

        if(currentPart >= lessonDetails.parts.size) {
            currentLesson++
            loadLessonDetails()
        } else {
            currentItem = lessonDetails.parts.get(currentPart)
            binder.text.text = currentItem?.item
        }

        binder.actions.visibility = View.INVISIBLE
        binder.actions.postDelayed({
            binder.actions.visibility = View.VISIBLE
        }, 5000)
    }


    fun speak() {
        if (tts.isSpeaking) return
        currentItem?.let {
            tts.speak(it.prono, TextToSpeech.QUEUE_ADD, null)
        }
    }

    override fun onInit(status: Int) {

    }
}
