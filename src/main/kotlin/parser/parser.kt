package parser

import parser.plotter.Plotter
import parser.solver.ParametrizedExpressionBuilder
import parser.solver.impl.custom.CustomParametrizedExpressionBuilder
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    if (args.size != 3) {
        help()
    } else {
        try {

            val xValues = generateXValues(args[1].split("to"))
            val yValues = mutableListOf<Double>()
            val equation = args[0]

            val expression = getExpressionBuilder(args[2])
            val expr = expression.build(equation)
            for (x in xValues) {
                yValues.add(expr.solve(x))
            }
            val plotter = Plotter()
            plotter.plot(xValues, yValues)

        } catch (e: Exception) {
            e.printStackTrace()
            help()
        }
    }
}

fun getExpressionBuilder(solverName: String): ParametrizedExpressionBuilder {

    if (solverName == "custom") {
        return CustomParametrizedExpressionBuilder()
    } else if (solverName == "mathjs") {
      //  return MathJsServiceSolver()
    }
    throw IllegalArgumentException("Cannot find solver with name -> $solverName")
}

fun help() {
    println("To run jar use: java -jar parser-task.jar x^2-10 -10to10 custom")
}

fun generateXValues(borders: List<String>): List<Double> {
    val result = mutableListOf<Double>()
    var current = borders[0].toDouble()
    val last = borders[1].toDouble()
    while (current <= last) {
        result.add(current)
        current += 1
    }
    return result
}
