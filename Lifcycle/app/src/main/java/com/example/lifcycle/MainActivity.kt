package com.example.lifcycle

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("LifeCycle", "OnCreate");
        findViewById<MaterialButton>(R.id.btnCheck).setOnClickListener {
            startActivity(Intent(this, DialogActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        mediaPlayer = MediaPlayer.create(this, R.raw.ai_2)

        Log.i("LifeCycle", "OnStart");
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.seekTo(position);
        mediaPlayer?.start();

        Log.i("LifeCycle", "OnResume");

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause();

        if(mediaPlayer !== null) position = mediaPlayer!!.currentPosition

        Log.i("LifeCycle", "OnPause");
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LifeCycle", "OnRestart");
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        Log.i("LifeCycle", "ONStop");

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LifeCycle", "destroy");

    }

}