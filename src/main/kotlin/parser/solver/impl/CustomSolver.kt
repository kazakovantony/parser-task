package parser.solver.impl

import parser.solver.Solver
import java.lang.StringBuilder
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.math.pow

class CustomSolver : Solver {

    override fun evaluate(expression: String): Double {

        val operators = Stack<String>()
        val values = Stack<Double>()

        val tokens = expression.toCharArray()
        var index = 0

        while (index < tokens.size) {
            val currentToken = tokens[index]

            try {

                when {
                    Character.isDigit(currentToken) -> {
                        val pair = extractValue(tokens, index)
                        values.push(pair.first)
                        index = pair.second
                    }
                    currentToken == '(' -> {
                        operators.push(currentToken.toString())
                        index++
                    }
                    currentToken == ')' -> {
                        resolveBracketContent(operators, values)
                        index++
                    }
                    isOperator(currentToken) -> {
                        var currentOperator = currentToken.toString()
                        if (isUnary(tokens, index, currentToken)) {
                            currentOperator = getUnary(currentOperator)
                        }
                        resolvePreviousPart(operators, values, currentOperator)
                        operators.push(currentOperator)
                        index++
                    }
                    else -> throw IllegalArgumentException("Wrong character at index -> $index")
                }

            } catch (e: RuntimeException) {
                throw IllegalArgumentException("Problem at index -> $index", e)
            }
        }

        while (!operators.empty())
            values.push(applyOperator(operators.pop(), values))
        return values.pop()
    }

    private fun getUnary(currentOperator: String): String {
        if (currentOperator == "-")
            return "u-"
        throw IllegalArgumentException("No unary mapping for operator: $currentOperator")
    }

    private fun isUnary(expression: CharArray, index: Int, currentToken: Char): Boolean {
        if (currentToken == '-') {
            if (index - 1 == -1 || expression[index - 1] == '(')
                return true
        }
        return false
    }

    private fun resolvePreviousPart(operators: Stack<String>, values: Stack<Double>, currentOperator: String) {
        while (!operators.empty() && hasPrecedence(currentOperator, operators.peek()))
            values.push(applyOperator(operators.pop(), values))
    }

    private fun hasPrecedence(currentOperator: String, previousOperator: String): Boolean {
        if (previousOperator == "(" || previousOperator == ")")
            return false
        if (currentOperator == "^" && "/+*-".contains(previousOperator))
            return false
        return !((currentOperator == "*" || currentOperator == "/") &&
                "+-".contains(previousOperator))
    }

    private fun isOperator(currentToken: Char): Boolean {
        return currentToken == '+' || currentToken == '-' ||
                currentToken == '*' || currentToken == '/' || currentToken == '^'
    }

    private fun resolveBracketContent(operators: Stack<String>, values: Stack<Double>) {
        while (operators.peek() != "(")
            values.push(applyOperator(operators.pop(), values))
        operators.pop()
    }

    private fun applyOperator(operator: String, values: Stack<Double>): Double {
        if (isUnary(operator)) {
            return applyUnaryOperator(operator, values.pop())
        }
        if (values.size < 2) {
            throw IllegalArgumentException("Wrong usage of operator: $operator")
        }
        return applyOperator(operator, values.pop(), values.pop())
    }

    private fun isUnary(operator: String): Boolean {
        if (operator.contains("u")) {
            return true
        }
        return false
    }

    private fun applyUnaryOperator(operator: String, value: Double): Double {
        when (operator) {
            "u-" -> return -value
        }
        throw IllegalStateException("Unreachable -> $operator")
    }

    private fun applyOperator(operator: String, second: Double, first: Double): Double {
        when (operator) {
            "+" -> return first + second
            "-" -> return first - second
            "*" -> return first * second
            "/" -> return first / second
            "^" -> return first.pow(second)
        }
        throw IllegalArgumentException("Wrong operator -> $operator")
    }

    private fun extractValue(tokens: CharArray, index: Int): Pair<Double, Int> {
        var currentIndex = index
        val doubleVal = StringBuilder()
        while (currentIndex < tokens.size &&
            (Character.isDigit(tokens[currentIndex]) ||
                    (tokens[currentIndex] == '.' && !doubleVal.contains('.')))
        ) {

            doubleVal.append(tokens[currentIndex])
            currentIndex += 1
        }
        return Pair(doubleVal.toString().toDouble(), currentIndex)
    }
}
