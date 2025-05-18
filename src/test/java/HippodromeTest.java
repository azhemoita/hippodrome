import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    @Test
    public void shouldThrowExceptionInConstructorWhenParameterIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowExceptionInConstructorWhenParameterIsEmpty() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(Collections.emptyList());
        });
        assertEquals("Horses cannot be empty.", illegalArgumentException.getMessage());
    }

    @Test
    public void getHorsesShouldReturnSameHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i + 1), i + 1));
        }

        List<Horse> horseList = new Hippodrome(horses).getHorses();
        assertEquals(horses, horseList);
    }

    @Mock
    List<Horse> horses;

    @Test
    public void shouldCallMoveForEachHorse() {
        horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Horse mock = mock(Horse.class);
            horses.add(mock);
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinnerShouldReturnHorseWithMaxDistance() {
        Horse firstHorse = mock(Horse.class);
        Horse secondHorse = mock(Horse.class);
        Horse thirdHorse = mock(Horse.class);
        Horse fourthHorse = mock(Horse.class);
        Horse fifthHorse = mock(Horse.class);

        Mockito.when(firstHorse.getDistance()).thenReturn(10.0);
        Mockito.when(secondHorse.getDistance()).thenReturn(11.0);
        Mockito.when(thirdHorse.getDistance()).thenReturn(9.0);
        Mockito.when(fourthHorse.getDistance()).thenReturn(12.0);
        Mockito.when(fifthHorse.getDistance()).thenReturn(8.0);

        List<Horse> horseList = List.of(firstHorse, secondHorse, thirdHorse, fourthHorse, fifthHorse);

        Horse winner = new Hippodrome(horseList).getWinner();

        assertSame(fourthHorse, winner);
    }

}
