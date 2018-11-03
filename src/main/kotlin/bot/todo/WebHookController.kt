package bot.todo

import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class WebHookController (
        val todoService: TodoService
) {

    @PostMapping(value = ["/line/webhook"])
    @ResponseStatus(value = HttpStatus.OK)
    fun handleLineWebHook(@RequestBody body: LineWebHookRequest) {
        val event = body.events[0]
        todoService.process(event)
    }
}

data class LineWebHookRequest (
    val events: List<MessageEvent<MessageContent>>
)