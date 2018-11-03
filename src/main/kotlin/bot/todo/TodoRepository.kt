package bot.todo

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository(value = "todo")
interface TodoRepository: ReactiveMongoRepository<Todo, String>, CustomTodoRepository {
    fun findByUser(user: String, sort: Sort): Flux<Todo>
}

interface CustomTodoRepository {
    fun markImportant(id: String): Mono<Todo>
    fun markFinished(id: String): Mono<Todo>
}

class CustomTodoRepositoryImpl (
        private val mongoTemplate: ReactiveMongoTemplate
): CustomTodoRepository {
    override fun markImportant(id: String): Mono<Todo> {
        val query = Query.query(Criteria.where("_id").`is`(id))
        val update = Update.update("isImportant", true)
        return mongoTemplate.findAndModify(query, update, Todo::class.java)
    }

    override fun markFinished(id: String): Mono<Todo> {
        val query = Query.query(Criteria.where("_id").`is`(id))
        val update = Update.update("isFinished", true)
        return mongoTemplate.findAndModify(query, update, Todo::class.java)
    }

}