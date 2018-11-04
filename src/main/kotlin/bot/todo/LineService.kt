package bot.todo

import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.ReplyMessage
import com.linecorp.bot.model.message.TextMessage
import io.netty.handler.codec.http.HttpHeaderNames
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class LineService(
        private val client: WebClient
) {
    fun reply(replyToken: String, message: String) {
        this.client
            .post()
            .uri("/bot/message/reply")
            .body(BodyInserters.fromObject(ReplyMessage(replyToken, TextMessage(message))))
            .exchange().subscribe()
    }

    fun sendMessage(user: String, message: String) {
        this.client
            .post()
            .uri("/bot/message/push")
            .body(BodyInserters.fromObject(PushMessage(user, TextMessage(message))))
            .exchange().subscribe()
    }
}

@Configuration
class LineWebClientConfiguration(
        val lineConfiguration: LineConfiguration
) {

    @Bean fun client()
            = WebClient.builder()
                .baseUrl("https://api.line.me/v2")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${lineConfiguration.lineToken}")
                .build()
}

@Configuration
@ConfigurationProperties(prefix = "myapp.line")
data class LineConfiguration(
        var lineToken: String = "",
        var lineSecret: String = ""
)