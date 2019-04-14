package org.baole.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.baole.learn.databinding.ActivityMainBinding
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.Observer
import java.util.*
import androidx.lifecycle.ViewModelProviders
import org.baole.learn.data.Lessons
import org.baole.learn.data.LessonsViewModel


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

        binder.text.text = "bò"
        binder.pronouce.text = "bờ o bo huyền bò"

        loadData()
    }

    fun loadData() {
        val viewModel = ViewModelProviders.of(this).get(LessonsViewModel::class.java)
        viewModel.liveData.observe(this, Observer<Lessons> { t ->
            t?.let {
                onLessonsLoaded(it)
            }
        })
    }

    private fun onLessonsLoaded(lessons: Lessons) {
        lessons.lessons.forEach {
            Log.d(TAG,"data: ${it.name}/${it.json}")
        }
    }


    fun speak() {
        tts.speak(binder.pronouce.text.toString(), TextToSpeech.QUEUE_ADD, null)

    }

    override fun onInit(status: Int) {

    }
}
