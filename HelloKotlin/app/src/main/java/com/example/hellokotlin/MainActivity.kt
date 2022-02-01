package com.example.hellokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)
        var message: String = findViewById<TextView>(R.id.tvStatus).text.toString();

    }

    override fun onInit(status: Int) {
        if(status === TextToSpeech.SUCCESS) {
            findViewById<TextView>(R.id.tvStatus).text = "Hellow Kotlin!"
            tts!!.setLanguage(Locale("ES"))
        } else {
            findViewById<TextView>(R.id.tvStatus).text = "No Disponible..."
        }
    }

    private fun speak() {

        var message: String = findViewById<TextView>(R.id.etMessage).text.toString();
        if (message.isEmpty()){
            findViewById<TextView>(R.id.tvStatus).text = "Introduzca un Texto"
            message = "No puedo hablar si no escribes algo."
        }

        tts!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")


    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}