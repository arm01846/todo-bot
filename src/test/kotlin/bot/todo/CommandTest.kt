package bot.todo

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class CommandTest {

    @Test
    fun parse_validCommand_shouldCreateNewTask() {
        assertEquals(
                Todo("Buy milk", LocalDateTime.of(2018, 5, 3, 13, 0)),
                Command.parse("Buy milk : 3/5/18 : 13:00")
        )

        assertEquals(
                Todo("Finish writing shopping list", LocalDate.now().atTime(15, 30)),
                Command.parse("Finish writing shopping list : today : 15:30")
        )

        assertEquals(
                Todo("Watch movie", LocalDate.now().plusDays(1).atTime(18, 0)),
                Command.parse("Watch movie : tomorrow : 18:00")
        )
    }
}
