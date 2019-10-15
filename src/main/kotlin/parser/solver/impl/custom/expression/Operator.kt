package parser.solver.impl.custom.expression

import kotlin.math.pow

class Operator (private val left: Node, private val alias: String, private val right: Node) :
    Node {

    override fun getValue(parameter: Double): Double {
        return applyOperator(alias, left.getValue(parameter), right.getValue(parameter))
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
}