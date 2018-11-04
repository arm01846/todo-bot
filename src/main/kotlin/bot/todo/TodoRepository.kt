package bot.todo

import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Repository(value = "todo")
interface TodoRepository: ReactiveMongoRepository<Todo, String>, CustomTodoRepository {
    fun findByUser(user: String, sort: Sort): Flux<Todo>
}

interface CustomTodoRepository {
    fun markImportant(id: String): Mono<Todo>
    fun markFinished(id: String): Mono<Todo>
    fun report(): Flux<TodoReport>
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

    override fun report(): Flux<TodoReport> {
        val query = newAggregation(
                match(Criteria.where("dateTime")
                    .gte(LocalDate.now())
                    .lt(LocalDate.now().plusDays(1))),
                group("user", "isFinished")
                        .first("user").`as`("user")
                        .first("isFinished").`as`("isFinished")
                        .count().`as`("count"),
                CustomProjectionAggregation(MongoQuery.CUSTOM_PROJECTION),
                CustomProjectionAggregation(MongoQuery.CUSTOM_GROUP))

        return mongoTemplate.aggregate(query, Todo::class.java, TodoReport::class.java)
    }
}

// Workaround for unsupported feature in spring-data-mongodb
class CustomProjectionAggregation(
        private val operation: Document
): AggregationOperation {

    override fun toDocument(context: AggregationOperationContext): Document {
        return context.getMappedObject(operation)
    }
}