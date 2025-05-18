import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Test
    public void shouldThrowExceptionInFirstConstructorWhenFirstParameterIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 5.0, 10.0);
        });
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowExceptionInSecondConstructorWhenFirstParameterIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 5.0);
        });
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    public void shouldThrowExceptionInFirstConstructorWhenFirstParameterIsEmpty(String input) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(input, 5.0, 10.0);
        });
        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    public void shouldThrowExceptionInSecondConstructorWhenFirstParameterIsEmpty(String input) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(input, 5.0);
        });
        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowExceptionInFirstConstructorWhenSecondParameterIsNegative() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Pegasus", -5.0, 10.0);
        });
        assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowExceptionInSecondConstructorWhenSecondParameterIsNegative() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Pegasus", -5.0);
        });
        assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowExceptionInFirstConstructorWhenThirdParameterIsNegative() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Pegasus", 5.0, -10.0);
        });
        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    public void getNameShouldReturnParameterName() {
        Horse horse = new Horse("Pegasus", 5.0, 10.0);
        String name = horse.getName();
        assertEquals("Pegasus", name);
    }

    @Test
    public void getSpeedShouldReturnParameterSpeed() {
        Horse horse = new Horse("Pegasus", 5.0, 10.0);
        double speed = horse.getSpeed();
        assertEquals(5.0, speed, 0.01);
    }

    @Test
    public void getDistanceShouldReturnParameterDistance() {
        Horse horse = new Horse("Pegasus", 5.0, 10.0);
        double distance = horse.getDistance();
        assertEquals(10.0, distance, 0.01);
    }

    @Test
    public void getDistanceShouldReturnZero() {
        Horse horse = new Horse("Pegasus", 5.0);
        double distance = horse.getDistance();
        assertEquals(0, distance);
    }

    @Test
    public void getRandomDoubleShouldReturnCorrectParameters() {
        Horse pegasus = new Horse("Pegasus", 5.0, 10.0);

        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class, Mockito.CALLS_REAL_METHODS)) {
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            pegasus.move();

            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "3.0,5.0,0.5,6.5",
            "2.0,1.0,0.2,1.4"
    })
    public void shouldUpdateDistanceCorrectly(double distance, double speed, double random, double expected) {
        Horse pegasus = new Horse("Pegasus", 5.0, 10.0);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class, Mockito.CALLS_REAL_METHODS)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            pegasus.move();

            assertEquals(distance, pegasus.getDistance(), 0.01);
        }
    }
}
