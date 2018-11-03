package bot.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoBotApplication

fun main(args: Array<String>) {
    runApplication<TodoBotApplication>(*args)
}
