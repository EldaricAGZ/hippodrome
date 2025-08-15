import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HorseTest {

    private final Horse HORSE = new Horse("Name", 1, 1);

    @Nested
    class ConstructorTest {

        private Throwable throwable;

        @Test
        void whenAssertingExceptionByFirstParameterInConstructor() {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                new Horse(null, 0);
            });
        }

        @Test
        void whenAssertingExceptionWithMassageByFirstParameterInConstructor() {
            whenAssertingExceptionByFirstParameterInConstructor();
            assertEquals("Name cannot be null.", throwable.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "\t", " "})
        void whenAssertingExceptionByFirstParameterInConstructorSetIncorrectValue(String string) {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                new Horse(string, 0);
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "\t", " "})
        void whenAssertingExceptionWithMassageByFirstParameterInConstructorSetIncorrectValue(String string) {
            whenAssertingExceptionByFirstParameterInConstructorSetIncorrectValue(string);
            assertEquals("Name cannot be blank.", throwable.getMessage());
        }

        @Test
        void whenAssertingExceptionBySecondParameterInConstructorSetNegativeValue() {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                new Horse("Horse", -1);
            });
        }

        @Test
        void whenAssertingExceptionWithMassageBySecondParameterInConstructorSetNegativeValue() {
            whenAssertingExceptionBySecondParameterInConstructorSetNegativeValue();
            assertEquals("Speed cannot be negative.", throwable.getMessage());
        }

        @Test
        void whenAssertingExceptionByThirdParameterInConstructorSetNegativeValue() {
            throwable = assertThrows(IllegalArgumentException.class, () -> {
                new Horse("Horse", 1, -1);
            });
        }

        @Test
        void whenAssertingExceptionWithMassageByThirdParameterInConstructorSetNegativeValue() {
            whenAssertingExceptionByThirdParameterInConstructorSetNegativeValue();
            assertEquals("Distance cannot be negative.", throwable.getMessage());
        }
    }

    @Test
    void getName() {
        assertEquals("Name", HORSE.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(1, HORSE.getSpeed());
    }

    @Nested
    class GetDistanceTest {
        @Test
        void whenGetDistanceReturnedNumberSettingThirdParameterInConstructor() {
            assertEquals(1, HORSE.getDistance());
        }

        @Test
        void whenGetDistanceReturnedZeroIfNotSettingThirdParameterInConstructor() {
            Horse horse = new Horse("Name", 1);
            assertEquals(0, horse.getDistance());
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class Move {

        @Test
        void whenMethodReturnedInsideMethodGetRandomDoubleWithParameters() {
            try(MockedStatic <Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
                HORSE.move();
                horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            }
        }

       @Test
        void whenMethodAssignsDistanceValueCalculateUsingTheFormula() {
           try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
               horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.9);
               Horse horse = new Horse("Name", 1, 1);
               horse.move();
               assertEquals(1.9, horse.getDistance());
           }
       }

    }

}