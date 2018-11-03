package bot.todo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: ReactiveMongoRepository<Todo, String>