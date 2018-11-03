package bot.todo

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class CommandTest {

    @Test
    fun parse_validCommand_shouldCreateTodo() {
        assertEquals(
                Todo("test", "Buy milk", LocalDateTime.of(2018, 5, 3, 13, 0)),
                Command.parse("test", "Buy milk : 3/5/18 : 13:00")
        )

        assertEquals(
                Todo("test", "Buy milk", LocalDateTime.of(2018, 5, 3, 13, 0)),
                Command.parse("test", "Buy milk : 03/05/18 : 13:00")
        )

        assertEquals(
                Todo("test", "Finish writing shopping list", LocalDate.now().atTime(15, 30)),
                Command.parse("test", "Finish writing shopping list : today : 15:30")
        )

        assertEquals(
                Todo("test", "Watch movie", LocalDate.now().plusDays(1).atTime(18, 0)),
                Command.parse("test", "Watch movie : tomorrow : 18:00")
        )

        assertEquals(
                Todo("test", "Watch movie", LocalDateTime.of(2018, 12, 30, 8, 0)),
                Command.parse("test", "Watch movie : 30/12/18 : 8:00")
        )

        assertEquals(
                Todo("test", "Watch movie", LocalDateTime.of(2018, 12, 30, 12, 0)),
                Command.parse("test", "Watch movie : 30/12/18")
        )
    }
}
