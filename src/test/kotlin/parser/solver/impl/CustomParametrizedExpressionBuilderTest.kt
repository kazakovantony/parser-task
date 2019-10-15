package parser.solver.impl

import org.junit.Test
import parser.solver.impl.custom.CustomParametrizedExpressionBuilder
import kotlin.test.assertEquals

class CustomParametrizedExpressionBuilderTest {

    private val instance = CustomParametrizedExpressionBuilder()

    @Test
    fun givenExpressionWithDifferentOperatorsPrecedenceWhenEvaluateThenReturnValidResult() {
        val parameterizedExpression = instance.build("-3+2*8*x/(4-2)")
        assertEquals(13.0, parameterizedExpression.solve(2.0))
    }

    @Test
    fun givenExpressionWithUnaryMinusesWhenEvaluateThenReturnValidResult() {
        val parameterizedExpression = instance.build("-3+2-2*(-9*x-7)")
        assertEquals(337.0, parameterizedExpression.solve(2.0))
    }

    @Test
    fun givenExpressionWithExponentiationMinusesWhenEvaluateThenReturnValidResult() {
        var parameterizedExpression = instance.build("(-9*x-7)^2")
        assertEquals(1156.0, parameterizedExpression.solve(3.0))
        parameterizedExpression = instance.build(("2^(9-7*x)^2"))
        assertEquals(16.0, parameterizedExpression.solve(1.0))
        parameterizedExpression = instance.build("(9-6)^(7-5)^(2*x)")
        assertEquals(43046721.0, parameterizedExpression.solve(2.0))
    }
}