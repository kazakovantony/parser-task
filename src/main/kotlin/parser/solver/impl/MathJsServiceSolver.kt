package parser.solver.impl

import org.jsoup.Jsoup
import java.net.URLEncoder

class MathJsServiceSolver {

    companion object {
        const val API_URL = "https://api.mathjs.org/v4/?expr="
    }

    fun evaluate(expression: String) {
        val doc = Jsoup.connect(API_URL + URLEncoder.encode(expression, "UTF-8")).get()
         //ParameterizedExpression(Value(doc.select("body").text().toDouble()))
    }
}