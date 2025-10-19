package com.example.calculator

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.calculator.databinding.FragmentMainBinding
import kotlin.math.round

class MainFragment: Fragment() {
//    val operators: List<String> = listOf("+", "-", "✕", "÷")
    private var _binding : FragmentMainBinding? = null
    private val binding : FragmentMainBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayout: GridLayout = binding.keyboard

        val expression: TextView = binding.expression
        val result: TextView = binding.result

        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            if (view is Button) {

                if (view.tag == "num") {
                    view.setOnClickListener {
                        if (Utils().isCorrectNum(expression.text, view.text.toString())){
                            expression.append(view.text)
                        }
                    }
                }
                else if (view.tag == "point") {
                    view.setOnClickListener {
                        if (Utils().isCorrectPlaceToPoint(expression.text.toString())
                        ) {
                            expression.append(view.text)
                        }
                    }
                }
                else if (view.tag == "operator") {
                    view.setOnClickListener {
                        if (Utils().isCorrectPlaceToOperator(expression.text, view.text.toString())) {
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
                        if (Utils().isCorrectPlaceToEquals(expression.text)){
                            val res: String = Calculator().calculate(expression.text.toString())
                            result.text = Utils().formatNumber(res)
                            expression.text = ""
                        }
                    }
                }
            } else {
                throw Exception("Invalid view type")
            }
        }

        videoView = binding.videoView
        playButton = binding.btnRR

        val mediaController = MediaController(context)

        Utils().play(videoView, playButton, mediaController)
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }
    private lateinit var videoView: VideoView
    private lateinit var playButton: Button
}