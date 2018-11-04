package bot.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.MessageContent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec



@RestController
class WebHookController (
        val todoService: TodoService,
        val lineConfiguration: LineConfiguration
) {

    val jsonMapper = ObjectMapper()

    @PostMapping(value = ["/line/webhook"])
    @ResponseStatus(value = HttpStatus.OK)
    fun handleLineWebHook(
            @RequestHeader(value = "X-Line-Signature") signature: String,
            @RequestBody body: LineWebHookRequest) {
        if (!validateSignature(signature, jsonMapper.writeValueAsString(body))) {
            return
        }

        val event = body.events[0]
        todoService.process(event)
    }

    fun validateSignature(signature: String, body: String): Boolean {
        val key = SecretKeySpec(lineConfiguration.lineToken.toByteArray(), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(key)
        val source = body.toByteArray()
        val calculatedSignature = Base64.getEncoder().encode(mac.doFinal(source))
        return signature.toByteArray().contentEquals(calculatedSignature)
    }
}

data class LineWebHookRequest (
    val events: List<MessageEvent<MessageContent>>
)