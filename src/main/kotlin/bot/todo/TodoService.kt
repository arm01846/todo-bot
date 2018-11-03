package bot.todo

import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import com.linecorp.bot.model.event.message.TextMessageContent
import org.springframework.stereotype.Service

@Service
class TodoService (
        val todoRepository: TodoRepository
) {

    fun process(event: MessageEvent<MessageContent>) {
        val user = event.source.userId
        val message = event.message
        when (message) {
            is TextMessageContent -> {
                val textCommand = message.text
                val todo = Command.parse(textCommand)
                todo.user = user
                this.todoRepository.save(todo).subscribe()
            }
        }
    }
}