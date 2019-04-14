package org.baole.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.baole.learn.databinding.ActivityMainBinding
import android.speech.tts.TextToSpeech
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

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
    }

    fun speak() {
        tts.speak(binder.pronouce.text.toString(), TextToSpeech.QUEUE_ADD, null)

    }

    override fun onInit(status: Int) {

    }
}
