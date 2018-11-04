package bot.todo

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TodoReporter (
        private val lineService: LineService,
        private val todoRepository: TodoRepository
) {

    @Scheduled(cron="0 0 12,18 * * *", zone="Asia/Bangkok")
    fun report() {
        (this.todoRepository as CustomTodoRepository).report()
                .subscribe {
                    it -> lineService.sendMessage(
                        it.user,
                        "${it.finished} tasks done, ${it.unfinished} tasks to do."
                    )
                }
    }
}