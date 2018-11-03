package bot.todo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository(value = "todo")
interface TodoRepository: ReactiveMongoRepository<Todo, String>