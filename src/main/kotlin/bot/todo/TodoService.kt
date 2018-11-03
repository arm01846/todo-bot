package bot.todo

import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import com.linecorp.bot.model.event.message.TextMessageContent
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TodoService (
        val todoRepository: TodoRepository,
        val lineService: LineService
) {

    fun process(event: MessageEvent<MessageContent>) {
        val user = event.source.userId
        val message = event.message
        when (message) {
            is TextMessageContent -> {
                val textCommand = message.text
                if (textCommand == "edit") {
                    lineService.reply(event.replyToken, "line://app/1619431141-PZJBpK4J")
                    return
                }
                val todo = Command.parse(textCommand)
                todo.user = user
                this.todoRepository.save(todo).subscribe()
            }
        }
    }

    fun markFinished(id: String): Mono<Todo>
        = (todoRepository as CustomTodoRepository).markFinished(id)
            .switchIfEmpty(Mono.error(NotFoundException()))

    fun markImportant(id: String): Mono<Todo>
        = (todoRepository as CustomTodoRepository).markFinished(id)
            .switchIfEmpty(Mono.error(NotFoundException()))
}

class NotFoundException: Exception()