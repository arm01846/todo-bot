package bot.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@SpringBootApplication
class TodoBotApplication

fun main(args: Array<String>) {
    runApplication<TodoBotApplication>(*args)
}

@Configuration
@EnableAsync
@EnableScheduling
class TodoConfiguration {

    @Bean
    fun threadPoolTaskExecutor() = ThreadPoolTaskExecutor()
}