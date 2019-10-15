package parser.solver

import parser.solver.impl.custom.expression.ParameterizedExpression

interface ParametrizedExpressionBuilder {
    fun build(expression: String): ParameterizedExpression
}