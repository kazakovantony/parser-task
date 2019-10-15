package parser.solver.impl.custom.expression

import parser.solver.impl.custom.expression.Argument

class Value(private val value: Double) : Argument {

    override fun getValue(parameter: Double): Double {
        return this.value
    }
}