package parser.solver.impl.custom.expression

import parser.solver.impl.custom.expression.Argument

class Parameter : Argument {

    override fun getValue(parameter: Double) : Double  {
        return parameter
    }
}