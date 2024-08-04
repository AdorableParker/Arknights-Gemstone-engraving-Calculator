package org.example.tools

object Tools {
    private val romanNumerals = arrayOf(
        Pair(1000, "M"), Pair(900, "CM"), Pair(500, "D"),
        Pair(400, "CD"), Pair(100, "C"), Pair(90, "XC"),
        Pair(50, "L"), Pair(40, "XL"), Pair(10, "X"),
        Pair(9, "IX"), Pair(5, "V"), Pair(4, "IV"), Pair(1, "I")
    )
    fun intToRoman(num:Int): String {
        var number = num
        val sb = StringBuilder()
        for (pair in romanNumerals) {
            while (number >= pair.first) {
                sb.append(pair.second)
                number -= pair.first
            }
        }
        return sb.toString()
    }
}
