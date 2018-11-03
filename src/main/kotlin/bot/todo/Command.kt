package bot.todo

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Command {
    fun parse(textCommand: String): Todo {
        val token = textCommand.split(':', limit=3)
        val message = token[0].trim()
        val date = when (token[1].trim()) {
            "today" -> LocalDate.now()
            "tomorrow" -> LocalDate.now().plusDays(1)
            else -> LocalDate.parse(token[1].trim(), DateTimeFormatter.ofPattern("d/M/yy"))
        }

        val time = LocalTime.parse(token[2].trim(), DateTimeFormatter.ofPattern("HH:mm"))

        return Todo(message, LocalDateTime.of(date, time))
    }
}

data class Todo(
        val message: String,
        val dateTime: LocalDateTime
)