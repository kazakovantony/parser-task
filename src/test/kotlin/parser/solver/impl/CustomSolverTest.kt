package parser.solver.impl

import org.junit.Test
import kotlin.test.assertEquals

class CustomSolverTest {

    private val instance = CustomSolver()

    @Test
    fun givenExpressionWithDifferentOperatorsPrecedenceWhenEvaluateThenReturnValidResult() {
        assertEquals(5.0, instance.evaluate("-3+2*8/(4-2)"))
    }

    @Test
    fun givenExpressionWithUnaryMinusesWhenEvaluateThenReturnValidResult() {
        assertEquals(31.0, instance.evaluate("-3+2-2*(-9-7)"))
    }

    @Test
    fun givenExpressionWithExponentiationMinusesWhenEvaluateThenReturnValidResult() {
        assertEquals(256.0, instance.evaluate("(-9-7)^2"))
        assertEquals(16.0, instance.evaluate("2^(9-7)^2"))
        assertEquals(81.0, instance.evaluate("(9-6)^(7-5)^2"))
    }
}