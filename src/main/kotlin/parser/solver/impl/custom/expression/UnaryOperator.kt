package parser.solver.impl.custom.expression

class UnaryOperator(private val argument: Argument, private val alias: String) :
    Node {
    override fun getValue(parameter: Double): Double {
        if(alias == "u-") return - argument.getValue(parameter)
        throw IllegalArgumentException("Do not have operator -> $alias")
    }
}