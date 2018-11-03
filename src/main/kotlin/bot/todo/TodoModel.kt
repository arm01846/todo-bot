package bot.todo

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


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


data class TodoReport(
        val user: String,
        val finished: Int,
        val unfinished: Int
)