package org.baole.learn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.baole.learn.data.*
import org.baole.learn.databinding.LessonFragmentBinding


class LessonFragment : BaseFragment() {

    val TAG = LessonFragment::class.java.simpleName

    private lateinit var binder: LessonFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = LessonFragmentBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    lateinit var detailViewModel: LessonDetailsViewModel

    private lateinit var viewModel: LessonsViewModel

    fun loadData() {
        activity?.let {
            viewModel = ViewModelProviders.of(it, LessonsViewModel.Factory(it.application)).get(LessonsViewModel::class.java)
            viewModel.liveData.observe(this, Observer<Lessons> { t ->
                t?.let {
                    onLessonsLoaded(it)
                } ?: Log.e(TAG, "Error while loading lessons")
            })

            detailViewModel = ViewModelProviders.of(it).get(LessonDetailsViewModel::class.java)
        }
    }

    private fun onLessonsLoaded(lessons: Lessons) {
        lessons.lessons.forEach {
            Log.d(TAG, "data: ${it.name}/${it.json}")
        }
        this.lessons = lessons
        this.currentLesson = lessons.selectedLesson
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

            viewModel.saveLastLesson(it)
        } ?: Log.e(TAG, "Error while loading lessons $currentLesson")
    }

    lateinit var lessonDetails: LessonDetail
    var currentPart = 0
    var currentLesson = 0
    var currentItem: LessonPart? = null
    lateinit var lessons: Lessons
    private fun onLessonDetailsLoaded(details: LessonDetail, selectEnd: Boolean = false) {
        this.lessonDetails = details
        this.currentPart = if (selectEnd) details.parts.size - 1 else 0
        details.parts.forEach {
            Log.d(TAG, "data: ${it.item}/${it.prono}")
        }

        loadPart()
    }

    fun loadPart() {
        if (!::lessonDetails.isInitialized) return

        if (currentPart >= lessonDetails.parts.size) {
            currentLesson++
            loadLessonDetails()
        } else {
            currentItem = lessonDetails.parts.get(currentPart)
            binder.text.text = currentItem?.item
        }

        binder.actionSpeak.isEnabled = false
        binder.actionSpeak.postDelayed({
            binder.actionSpeak.isEnabled = true
        }, 5000)
    }


    fun speak() {
        if (tts.isSpeaking) return
        currentItem?.let {
            tts.speak(it.prono, TextToSpeech.QUEUE_ADD, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lesson, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_lesson_list) {
            showLessonList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLessonList() {
        val tag = LessonListFragment::class.java.simpleName
        val fragment = LessonListFragment()
        fragment.onSelectItem = {
            val selectedPosition = this.lessons.lessons.indexOf(it)
            if (selectedPosition >= 0) {
                this.currentLesson = selectedPosition
                loadLessonDetails()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, fragment, tag)
            ?.addToBackStack(tag)?.commit()
    }

}
