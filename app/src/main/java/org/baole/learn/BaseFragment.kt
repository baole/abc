package org.baole.learn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import java.util.*

open class BaseFragment : Fragment(), TextToSpeech.OnInitListener {
    protected lateinit var tts: TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(context, this)
        tts.language = Locale("vi")
    }


    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
    override fun onInit(status: Int) {

    }
}