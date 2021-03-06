package com.example.exam.utils

val apiKey = "0a61776894a2e3b276406a77992eceea"
val hodHaSharonCityID = "294760"
val hodHaSharonLat = "32.0114"
val hodHaSharonLon = "34.7722"


val MINUTE = 1000 * 60
val DAY = MINUTE * 60 * 24


fun getLocalizedName(name: String): CharSequence? {
    when(name){
        "SUNDAY" -> return "ראשון"
        "MONDAY" -> return "שני"
        "TUESDAY" -> return "שלישי"
        "WEDNESDAY" -> return "רביעי"
        "THURSDAY" -> return "חמישי"
        "FRIDAY" -> return "שישי"
        "SATURDAY" -> return "שבת"
        else -> return "N/A"
    }
}