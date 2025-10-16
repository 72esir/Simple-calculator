package com.example.calculator

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Calculator
import javax.xml.xpath.XPathExpression
import kotlin.math.log
import kotlin.math.round


class MainActivity : AppCompatActivity() {
    val operators: List<String> = listOf("+", "-", "✕", "÷")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.keyboard)

        val expression: TextView = findViewById(R.id.expression)
        val result: TextView = findViewById(R.id.result)

        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            if (view is Button) {

                if (view.tag == "num") {
                    view.setOnClickListener {
                        if (isCorrectNum(expression.text, view.text.toString())){
                            expression.append(view.text)
                        }
                    }
                }
                else if (view.tag == "point") {
                    view.setOnClickListener {
                        if (isCorrectPlaceToPoint(expression.text.toString())
                        ) {
                            expression.append(view.text)
                        }
                    }
                }
                else if (view.tag == "operator") {
                    view.setOnClickListener {
                        if (isCorrectPlaceToOperator(expression.text, view.text.toString())) {
                            expression.append(view.text)
                        }
                    }
                }
                else if (view.id == R.id.btn_delete) {
                    view.setOnClickListener {
                        if (expression.text.isNotEmpty()) {
                            var ex = expression.text
                            ex = ex.substring(0, ex.length - 1)
                            expression.text = ex
                        }
                    }
                } else if (view.id == R.id.btn_clear) {
                    view.setOnClickListener {
                        result.text = ""
                        expression.text = ""
                    }
                } else if (view.tag == "equals") {
                    view.setOnClickListener {
                        val res: String = Calculator().calculate(expression.text.toString())
                        result.text = formatNumber(res)
//                        result.text = res
                        expression.text = ""
                    }
                }
            } else {
                throw Exception("Invalid view type")
            }
        }

        videoView = findViewById<VideoView>(R.id.videoView)
        playButton = findViewById<Button>(R.id.btn_RR)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.videoplayback}")
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

    private fun formatNumber(value: String): String {
        val num = value.toDoubleOrNull() ?: return value
        val rounded = (round(num * 100) / 100)

         return if (rounded % 1.0 == 0.0) {
            rounded.toInt().toString()
        } else {
            String.format("%.2f", rounded)
        }
    }
    private fun isCorrectPlaceToOperator(expression: CharSequence, token: String): Boolean {
        if (expression.isEmpty()){
            return false
        }
        else if (expression.last().toString() == "."){
            return false
        }
        else if (!expression.isEmpty()) {
            return !(operators.contains(expression.last().toString()) && operators.contains(token))
        }
        else {
            return true
        }
    }

    private fun isCorrectPlaceToPoint(expression: String): Boolean {
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

    private fun isCorrectNum(expression: CharSequence, token: String): Boolean {
        if (expression.isEmpty() && token == "0") {
            return false
        }
        else if (!expression.isEmpty() && operators.contains(expression.last().toString()) && token == "0") {
            return false
        }
        else {
            return true
        }
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }
    private lateinit var videoView: VideoView
    private lateinit var playButton: Button
    private var isVideoVisible = false
}
