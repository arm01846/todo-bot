package bot.todo

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
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

        val time = if (token.size > 2) {
            LocalTime.parse(token[2].trim(), DateTimeFormatter.ofPattern("H:m"))
        } else {
            LocalTime.of(12, 0)
        }

        return Todo(message, LocalDateTime.of(date, time))
    }
}

@Document(collection = "todoRepository")
@CompoundIndex(
        name = "todo_index",
        def = "{'user' : 1, 'isImportant': -1, 'dateTime': 1}"
)
data class Todo(
        val message: String,
        val dateTime: LocalDateTime
) {
    lateinit var id: String
    lateinit var user: String
    var isImportant: Boolean = false
    var isFinished: Boolean = false
}