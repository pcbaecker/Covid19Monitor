package com.pcbaecker

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory

class WorldwideClientImpl : WorldwideClient {

    val log = LoggerFactory.getLogger(javaClass)!!

    override fun download(): WorldwideData {
        val ww = WorldwideData()

        Jsoup.connect("https://www.worldometers.info/coronavirus/").get().run {
            parseTotal(ww, select(".maincounter-number"))
            parseCountries(ww, select("table#main_table_countries_today"))
        }

        log.info("Downloaded new data = $ww")

        return ww
    }

    private fun parseCountries(ww: WorldwideData, root: Elements) {
        var country = WorldwideCountry()
        root.select("table tbody tr td").forEachIndexed { j, e ->
            if (e.hasText()) {
                val i = j % 9
                if (i == 0) {
                    country.name = e.text()
                }
                if (i == 1) {
                    country.infected = e.text().replace(",", "").toInt()
                }
                if (i == 2) {
                    country.newInfected = e.text().replace(",", "").toInt()
                }
                if (i == 3) {
                    country.deaths = e.text().replace(",", "").toInt()
                }
                if (i == 4) {
                    country.newDeaths = e.text().replace(",", "").toInt()
                }
                if (i == 5) {
                    country.recovered = e.text().replace(",", "").toInt()
                }
                if (i == 6) {
                    country.currentInfections = e.text().replace(",", "").toInt()
                }
                if (i == 7) {
                    country.seriousCriticalInfections = e.text().replace(",", "").toInt()
                }
                if (i == 8) {
                    country.infectionsPerOneMillionPopulation = e.text().replace(",", "").toFloat()
                    ww.countries.add(country)
                    country = WorldwideCountry()
                }
            }
        }
    }

    private fun parseTotal(ww: WorldwideData, root: Elements) {
        root.forEachIndexed { i, e ->
            if (e.childrenSize() > 0 && e.child(0).text() != null) {
                val count = e.child(0).text().replace(",", "")

                if (i == 0) {
                    ww.totalInfected = count.toInt()
                } else if (i == 1) {
                    ww.totalDeaths = count.toInt()
                } else {
                    ww.totalRecovered = count.toInt()
                }
            }
        }
    }
}