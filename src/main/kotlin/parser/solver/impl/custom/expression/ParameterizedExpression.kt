package parser.solver.impl.custom.expression

class ParameterizedExpression(private val firstNode: Operator) {
    fun solve(parameter: Double): Double {
        return firstNode.getValue(parameter)
    }
}