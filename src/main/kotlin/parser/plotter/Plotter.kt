package parser.plotter

import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class Plotter {

    companion object {
        const val POINT_LABEL = "*"
        const val RIGHT_DELIMITER = " "
        const val POINT = "(%d,%d)"
    }

    fun plot(xValues: List<Double>, yValues: MutableList<Double>) {
        var currentLineIndex = yValues.max()!!.roundToInt()
        val minX = xValues.min()!!.roundToInt()
        val maxX = xValues.max()!!.roundToInt()
        val argumentsGraph = createGraph(xValues, yValues)
        val step = 1
        val minY = yValues.min()!!.roundToInt()
        println(createMetaLine(minX, maxX, currentLineIndex))
        while (currentLineIndex >= minY) {

            if (argumentsGraph.containsKey(currentLineIndex)) {
                val arguments = argumentsGraph[currentLineIndex]
                val line = createOutputLine(arguments, minX)
                println(line)
            } else {
                println()
            }

            currentLineIndex -= step
        }
        print(createMetaLine(minX, maxX, minY))
    }

    private fun createMetaLine(minX: Int, maxX: Int, y : Int): String {
        val sb = StringBuilder(POINT.format(minX, y))
        val whitespacesAmount = minX.minus(maxX).absoluteValue - sb.length
        for (i in whitespacesAmount downTo 1) {
            sb.append(RIGHT_DELIMITER)
        }
        sb.append(POINT.format(maxX, y))
        return sb.toString()
    }

    private fun createOutputLine(arguments: MutableList<Int>?, minX: Int): String {
        val sorted = arguments!!.sorted()
        val sb = StringBuilder()

        for (x in sorted) {
            val whitespacesAmount = minX.minus(x).absoluteValue - sb.length
            for (i in whitespacesAmount downTo 2) {
                sb.append(RIGHT_DELIMITER)
            }
            sb.append(POINT_LABEL)
        }
        return sb.toString()
    }

    private fun createGraph(xValues: List<Double>, yValues: MutableList<Double>): Map<Int, MutableList<Int>> {
        val result = mutableMapOf<Int, MutableList<Int>>()
        for ((index, value) in yValues.withIndex()) {
            val y = value.roundToInt()
            if (result.contains(y)) {
                result[y]!!.add(xValues[index].roundToInt())
            } else {
                result[y] = mutableListOf(xValues[index].roundToInt())
            }
        }
        return result
    }
}
