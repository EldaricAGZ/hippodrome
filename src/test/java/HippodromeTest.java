import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    private final static ArrayList<Horse> horses = new ArrayList<>();

    @Nested
    class Constructor {

        private Throwable throwable;

        @Test
        void whenSetNullOnConstructorAssertingException() {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                Hippodrome hippodrome = new Hippodrome(null);
            });
        }

        @Test
        void whenSetNullOnConstructorAssertingExceptionWithMassage() {
            whenSetNullOnConstructorAssertingException();
            assertEquals("Horses cannot be null.", throwable.getMessage());
        }

        @Test
        void whenSetEmptyListOnConstructorAssertingException() {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                ArrayList<Horse> horses = new ArrayList<>();
                Hippodrome hippodrome = new Hippodrome(horses);
            });
        }

        @Test
        void whenSetEmptyListOnConstructorAssertingExceptionWithMassage() {
            whenSetEmptyListOnConstructorAssertingException();
            assertEquals("Horses cannot be empty.", throwable.getMessage());
        }
    }

    @Test
    void getHorses() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Name " + i, 1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertArrayEquals(horses.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    void move() {
        horses.clear();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse hors : horses) {
            Mockito.verify(hors).move();
        }
    }

    @Test
    void getWinner() {
        horses.clear();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Name " + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        Horse horseWinner = hippodrome.
                getHorses().
                stream().
                max((x1, x2) -> (int) (x1.getDistance() - x2.getDistance())).
                get();
        assertEquals(horseWinner, hippodrome.getWinner());
    }
}