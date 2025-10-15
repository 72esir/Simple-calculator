package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.keyboard)

        val expression: TextView = findViewById(R.id.expression)
        val result: TextView = findViewById(R.id.result)

//        val btnDelete: Button = findViewById(R.id.btn_delete)
//        val btnClear: Button = findViewById(R.id.btn_clear)
//        val btnDivide: Button = findViewById(R.id.btn_divide)
//
//        val btn7: Button = findViewById(R.id.btn_7)
//        val btn8: Button = findViewById(R.id.btn_8)
//        val btn9: Button = findViewById(R.id.btn_9)
//        val btnMultiply: Button = findViewById(R.id.btn_multiply)
//
//        val btn4: Button = findViewById(R.id.btn_4)
//        val btn5: Button = findViewById(R.id.btn_5)
//        val btn6: Button = findViewById(R.id.btn_6)
//        val btnMinus: Button = findViewById(R.id.btn_minus)
//
//        val btn1: Button = findViewById(R.id.btn_1)
//        val btn2: Button = findViewById(R.id.btn_2)
//        val btn3: Button = findViewById(R.id.btn_3)
//        val btnPlus: Button = findViewById(R.id.btn_plus)
//
//        val btnRR: Button = findViewById(R.id.btn_RR)
//        val btn0: Button = findViewById(R.id.btn_0)
//        val btnPoint: Button = findViewById(R.id.btn_point)
//        val btnEquals: Button = findViewById(R.id.btn_equals)


//        btnDelete.setOnLongClickListener { TODO() }

        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            if (view is Button) {
                if (view.tag == "num" || view.tag == "operand"){
                    view.setOnClickListener { expression.append(view.text) }
                }
                else if (view.id == R.id.btn_delete){
                    view.setOnClickListener {
                        var ex = expression.text
                        ex = ex.substring(0, ex.length-1)
                        expression.text = ex
                    }
                }
                else if (view.id == R.id.btn_clear) {
                    view.setOnClickListener {
                        result.text = ""
                        expression.text = ""
                    }
                }
                else if (view.tag == "equals"){
//                    TODO()
                }
            }
            else{
//                TODO()
            }
        }

    }
}