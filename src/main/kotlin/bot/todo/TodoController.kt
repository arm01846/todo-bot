package bot.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["/api"])
class TodoController (
        private val todoService: TodoService
) {

    @PutMapping(value = ["/{user}/{id}/finished"])
    fun handleMarkFinished(
            @PathVariable("user") user: String,
            @PathVariable("id") id: String): Mono<Todo>
        = todoService.markFinished(id)

    @PutMapping(value = ["/{user}/{id}/important"])
    fun handleMarkImportant(
            @PathVariable("user") user: String,
            @PathVariable("id") id: String): Mono<Todo>
        = todoService.markImportant(id)

    @GetMapping(value = ["/{user}"])
    fun handleListTodo(
            @PathVariable("user") user: String
    ) = todoService.list(user)

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleNotFoundException() {}
}