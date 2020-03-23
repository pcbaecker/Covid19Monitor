package com.pcbaecker

data class WorldwideCountry(var name: String, var infected: Int, var newInfected: Int, var deaths: Int, var newDeaths: Int, var recovered: Int, var currentInfections: Int, var seriousCriticalInfections: Int, var infectionsPerOneMillionPopulation: Float) {
    constructor() : this("",0,0,0,0,0,0,0,0.0f)
}

data class WorldwideData(var totalInfected: Int, var totalDeaths: Int, var totalRecovered: Int, var countries: MutableList<WorldwideCountry>) {
    constructor() : this(0, 0, 0, ArrayList())
}