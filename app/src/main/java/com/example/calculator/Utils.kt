package com.example.calculator

import android.net.Uri
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import kotlin.math.round

class Utils {
    val operators: List<String> = listOf("+", "-", "✕", "÷")

    fun formatNumber(value: String): String {
        val num = value.toDoubleOrNull() ?: return value
        val rounded = (round(num * 100) / 100)

        return if (rounded % 1.0 == 0.0) {
            rounded.toInt().toString()
        } else {
            String.format("%.2f", rounded)
        }
    }
    fun isCorrectPlaceToOperator(expression: CharSequence, token: String): Boolean {
        return if (expression.isEmpty()){
            false
        } else if (expression.last().toString() == "."){
            false
        } else if (!expression.isEmpty()) {
            !(operators.contains(expression.last().toString()) && operators.contains(token))
        } else {
            true
        }
    }

    fun isCorrectPlaceToPoint(expression: String): Boolean {
        if (expression.isEmpty()) {
            return false
        }
        else if (operators.contains(expression.last().toString())){
            return false
        }
        else {
            val lastNumber = expression.split("+", "-", "✕", "÷").last()
            return !lastNumber.contains(".")
        }
    }

    fun isCorrectNum(expression: CharSequence, token: String): Boolean {
        return if (expression.isEmpty() && token == "0") {
            false
        } else if (!expression.isEmpty() && operators.contains(expression.last().toString()) && token == "0") {
            false
        } else {
            true
        }
    }

    fun isCorrectPlaceToEquals(expression: CharSequence): Boolean{
        return if (expression.isEmpty()){
            false
        }else if (expression.last().toString() == "."){
            false
        }else if (operators.contains(expression.last().toString())){
            false
        }else{
            true
        }
    }

    fun play(videoView: VideoView, playButton: Button, mediaController: MediaController){
        var isVideoVisible = false

        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri = Uri.parse("android.resource://com.example.calculator/${R.raw.videoplayback}")
        videoView.setVideoURI(videoUri)

        playButton.setOnClickListener {
            if (!isVideoVisible) {
                videoView.visibility = VideoView.VISIBLE
                videoView.start()
                isVideoVisible = true
            } else {
                videoView.pause()
                videoView.visibility = VideoView.GONE
                isVideoVisible = false
            }
        }
    }
}