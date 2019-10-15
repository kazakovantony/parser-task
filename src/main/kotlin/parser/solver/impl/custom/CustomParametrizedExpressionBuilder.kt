package parser.solver.impl.custom

import parser.solver.ParametrizedExpressionBuilder
import parser.solver.impl.custom.expression.*
import java.lang.StringBuilder
import java.util.*
import kotlin.IllegalArgumentException

class CustomParametrizedExpressionBuilder : ParametrizedExpressionBuilder {

    override fun build(expression: String): ParameterizedExpression {

        val operators = Stack<String>()

        val tokens = expression.toCharArray()
        var index = 0
        val nodes = Stack<Node>()

        while (index < tokens.size) {
            val currentToken = tokens[index]

            try {

                when {
                    Character.isDigit(currentToken) -> {
                        val pair = extractValue(tokens, index)
                        val value = Value(pair.first)
                        nodes.push(value)
                        index = pair.second
                    }
                    currentToken == '(' -> {
                        operators.push(currentToken.toString())
                        index++
                    }
                    currentToken == ')' -> {
                        resolveBracketContent(operators, nodes)
                        index++
                    }
                    currentToken == 'x' -> {
                        nodes.push(Parameter())
                        index++
                    }
                    isOperator(currentToken) -> {
                        var currentOperator = currentToken.toString()
                        if (isUnary(tokens, index, currentToken)) {
                            currentOperator = getUnary(currentOperator)
                        }
                        resolvePreviousPart(operators, currentOperator, nodes)
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
            addOperator(operators.pop(), nodes)
        return ParameterizedExpression(nodes.pop() as Operator)
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

    private fun resolvePreviousPart(operators: Stack<String>, currentOperator: String, nodes: Stack<Node>) {
        while (!operators.empty() && hasPrecedence(currentOperator, operators.peek()))
            addOperator(operators.pop(), nodes)
    }

    private fun hasPrecedence(currentOperator: String, previousOperator: String): Boolean {
        if (previousOperator == "(" || previousOperator == ")")
            return false
        if (currentOperator == "^" && "/+*-^".contains(previousOperator))
            return false
        return !((currentOperator == "*" || currentOperator == "/") &&
                "+-".contains(previousOperator))
    }

    private fun isOperator(currentToken: Char): Boolean {
        return currentToken == '+' || currentToken == '-' ||
                currentToken == '*' || currentToken == '/' || currentToken == '^'
    }

    private fun resolveBracketContent(operators: Stack<String>, nodes: Stack<Node>) {
        while (operators.peek() != "(")
            addOperator(operators.pop(), nodes)
        operators.pop()
    }

    private fun addOperator(operator: String, nodes: Stack<Node>) {
        if (isUnary(operator)) {
            nodes.push(UnaryOperator(nodes.pop() as Argument, operator))
            return
        }
        require(nodes.size >= 2) { "Wrong usage of operator: $operator" }
        nodes.push(Operator(nodes.pop(), operator, nodes.pop()))
    }

    private fun isUnary(operator: String): Boolean {
        if (operator.contains("u")) {
            return true
        }
        return false
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
