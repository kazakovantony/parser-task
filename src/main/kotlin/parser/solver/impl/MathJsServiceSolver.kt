package parser.solver.impl

import org.jsoup.Jsoup
import parser.solver.Solver
import java.net.URLEncoder

class MathJsServiceSolver : Solver {

    companion object {
        const val API_URL = "https://api.mathjs.org/v4/?expr="
    }

    override fun evaluate(expression: String): Double {
        val doc = Jsoup.connect(API_URL + URLEncoder.encode(expression, "UTF-8")).get()
        return doc.select("body").text().toDouble()
    }
}