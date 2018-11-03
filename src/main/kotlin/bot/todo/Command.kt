package bot.todo

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Command {
    fun parse(user: String, textCommand: String): Todo {
        val token = textCommand.split(':', limit=3)
        val message = token[0].trim()
        val date = when (token[1].trim()) {
            "today" -> LocalDate.now()
            "tomorrow" -> LocalDate.now().plusDays(1)
            else -> LocalDate.parse(token[1].trim(), DateTimeFormatter.ofPattern("d/M/yy"))
        }

        val time = if (token.size > 2) {
            LocalTime.parse(token[2].trim(), DateTimeFormatter.ofPattern("H:m"))
        } else {
            LocalTime.of(12, 0)
        }

        return Todo(user, message, LocalDateTime.of(date, time))
    }
}

@Document(collection = "todo")
@CompoundIndex(
        name = "todo_index",
        def = "{'user' : 1, 'isImportant': -1, 'dateTime': 1}"
)
data class Todo(
        val user: String,
        val message: String,
        val dateTime: LocalDateTime
) {
    val id: String? = null
    var isImportant: Boolean = false
    var isFinished: Boolean = false
}