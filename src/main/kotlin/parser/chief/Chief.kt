package parser.chief

class Chief {

    companion object {
        const val PARAMETER = "x"
    }

    fun makeExpression(function: String, variableToSwitch: Double): String {
        return function.replace(PARAMETER, "($variableToSwitch)")
    }
}
