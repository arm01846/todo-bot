package bot.todo

import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import com.linecorp.bot.model.event.message.TextMessageContent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class WebHookController {

    @PostMapping(value = ["/line/webhook"])
    @ResponseStatus(value = HttpStatus.OK)
    fun handleLineWebHook(body: LineWebHookRequest) {
        val message = body.events[0].message
        when (message) {
            is TextMessageContent -> println(message.text)
        }
    }
}

data class LineWebHookRequest (
    val events: List<MessageEvent<MessageContent>>
)