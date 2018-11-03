package bot.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class TodoController (
        private val todoService: TodoService
) {

    @PutMapping(value = ["/{id}/finished"])
    fun handleMarkFinished(@PathVariable("id") id: String): Mono<Todo>
        = todoService.markFinished(id)

    @PutMapping(value = ["/{id}/important"])
    fun handleMarkImportant(@PathVariable("id") id: String): Mono<Todo>
        = todoService.markImportant(id)

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleNotFoundException() {}
}