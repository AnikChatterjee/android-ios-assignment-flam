import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

data class WeatherData(
    val temperature: Double,
    val humidity: Double,
    val condition: String,
    val timestamp: LocalDateTime
)


val weatherStorage = mutableListOf<WeatherData>()


fun fetchWeatherFromApi(): WeatherData {
    val temperature = Random.nextDouble(20.0, 35.0)
    val humidity = Random.nextDouble(40.0, 90.0)
    val conditions = listOf("Sunny", "Cloudy", "Rainy", "Windy", "Stormy")
    val condition = conditions.random()
    val timestamp = LocalDateTime.now()

    return WeatherData(temperature, humidity, condition, timestamp)
}


fun saveWeatherData(data: WeatherData) {
    weatherStorage.add(data)
    println("Saved: $data")
}


fun simulateAutoSync(times: Int = 4) {
    println("Starting background sync...")
    repeat(times) {
        val data = fetchWeatherFromApi()
        saveWeatherData(data)
        Thread.sleep(200) 
    }
    println("Auto sync complete.\n")
}


fun showWeeklySummary() {
    println("Weekly Temperature Summary:\n")
    val recentData = weatherStorage.takeLast(7)

    recentData.forEach {
        val dateStr = it.timestamp.format(DateTimeFormatter.ofPattern("E HH:mm"))
        val barLength = it.temperature.toInt().coerceAtMost(50) // limit to 50
        val bar = "▓".repeat(barLength)
        println("$dateStr | ${"%.1f".format(it.temperature)}°C | $bar")
    }
}


fun showDetailsForDay(index: Int) {
    if (index < 0 || index >= weatherStorage.size) {
        println("No data available for the selected index.")
        return
    }
    val data = weatherStorage[index]
    val formattedTime = data.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    println(
        """
        |Detailed Weather:
        |Time: $formattedTime
        |Temperature: ${"%.1f".format(data.temperature)}°C
        |Humidity: ${"%.1f".format(data.humidity)}%
        |Condition: ${data.condition}
    """.trimMargin()
    )
}


fun main() {
    println("Welcome to WeatherTrack (Console Simulation)\n")


    println("Manually fetching current weather...")
    val currentData = fetchWeatherFromApi()
    saveWeatherData(currentData)


    simulateAutoSync()


    showWeeklySummary()

    println("\nSelect a day index (0 = oldest, ${weatherStorage.size - 1} = latest) to view details:")
    val index = readLine()?.toIntOrNull() ?: return
    showDetailsForDay(index)
}
