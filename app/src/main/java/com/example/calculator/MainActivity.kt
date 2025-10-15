package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Calculator
import javax.xml.xpath.XPathExpression
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    val operators: List<String> = listOf("+", "-", "✕", "÷")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.keyboard)

        val expression: TextView = findViewById(R.id.expression)
        val result: TextView = findViewById(R.id.result)

//        btnDelete.setOnLongClickListener { TODO() }

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
                        var ex = expression.text
                        ex = ex.substring(0, ex.length - 1)
                        expression.text = ex
                    }
                } else if (view.id == R.id.btn_clear) {
                    view.setOnClickListener {
                        result.text = ""
                        expression.text = ""
                    }
                } else if (view.tag == "equals") {
                    view.setOnClickListener {
                        result.text = Calculator().calculate(expression.text.toString())
                        expression.text = ""
                    }
                }
            } else {
                throw Exception("Invalid view type")
            }
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
}
