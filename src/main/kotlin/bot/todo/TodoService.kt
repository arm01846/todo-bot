package bot.todo

import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import com.linecorp.bot.model.event.message.TextMessageContent
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TodoService (
        val todoRepository: TodoRepository,
        val lineService: LineService
) {

    @Async
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

    fun markFinished(id: String): Flux<Todo>
        = (todoRepository as CustomTodoRepository).markFinished(id)
            .switchIfEmpty(Mono.error(NotFoundException()))
            .flatMapMany { it -> list(it.user) }

    fun markImportant(id: String): Flux<Todo>
        = (todoRepository as CustomTodoRepository).markImportant(id)
            .switchIfEmpty(Mono.error(NotFoundException()))
            .flatMapMany { it -> list(it.user) }

    fun list(user: String): Flux<Todo>
        = todoRepository.findByUser(
            user,
            Sort.by(Sort.Order.desc("isImportant"), Sort.Order.asc("isFinished"))
    )
}

class NotFoundException: Exception()