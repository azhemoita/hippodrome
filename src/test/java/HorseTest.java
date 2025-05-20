import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.stubbing.OngoingStubbing;

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
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            new Horse("Pegasus", 5.0, 10.0).move();

            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "3.0,5.0,0.5,5.5",
            "2.0,1.0,0.2,2.2"
    })
    public void shouldUpdateDistanceCorrectly(double distance, double speed, double random, double expected) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            new Horse("Pegasus", speed, distance).move();

            assertEquals(expected, distance + speed * Horse.getRandomDouble(0.2, 0.9));
        }
    }
}
