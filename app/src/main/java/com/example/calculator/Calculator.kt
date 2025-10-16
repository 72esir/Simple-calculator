package com.example.calculator

class Calculator {
    fun calculate(expression: String): String {
        if (expression.isBlank()) return "0"

        val regex = """(\d+(\.\d+)?|[+\-✕÷])""".toRegex()
        val tokens = regex.findAll(expression).map { it.value }.toMutableList()

        var i = 0
        while (i < tokens.size) {
            when (tokens[i]) {
                "✕", "÷" -> {
                    processOperator(tokens, i, tokens[i])
                    i--
                }
                else -> i++
            }
        }

        i = 0
        while (i < tokens.size) {
            when (tokens[i]) {
                "+", "-" -> {
                    processOperator(tokens, i, tokens[i])
                    i--
                }
                else -> i++
            }
        }
        return tokens[0]
    }

    private fun processOperator(tokens: MutableList<String>, index: Int, operator: String) {
        if (index == 0 || index == tokens.size - 1) return

        val a = tokens[index - 1].toDouble()
        val b = tokens[index + 1].toDouble()
        val res = when (operator) {
            "+" -> a + b
            "-" -> a - b
            "✕" -> a * b
            "÷" -> a / b
            else -> 0.0
        }

        tokens[index] = res.toString()
        tokens.removeAt(index + 1)
        tokens.removeAt(index - 1)
    }

}