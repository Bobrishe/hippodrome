import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    public static final int HORSES_LIMIT = 30;
    public static final int MOCKED_HORSES_LIMIT = 50;

    @Test
    public void shouldThrowExceptionIfArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void shouldReturnCorrectMessageIfArgIsNull() {
        String expectedMessage = "Horses cannot be null.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfArgIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
    }

    @Test
    public void shouldReturnCorrectMessageIfArgIsEmpty() {
        String expectedMessage = "Horses cannot be empty.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(List.of()));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldMethodGetHorsesReturnSameListWithConstructor() {

        List<Horse> horses = getRandomHorses();

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());

    }

    @Test
    public void shouldMethodMoveCallsForEachHorses() {

        List<Horse> horses = new ArrayList<>(MOCKED_HORSES_LIMIT);

        IntStream.range(0, MOCKED_HORSES_LIMIT).forEach(random -> horses.add(mock(Horse.class)));

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        horses.forEach(horse -> Mockito.verify(horse).move());

    }

    @Test
    public void shouldGetWinnerReturnHorseWithLongerDistance() {
        List<Horse> horses = getRandomHorses();

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        OptionalDouble expectedHorse = horses.stream()
                .mapToDouble(Horse::getDistance)
                .max();

        assertEquals(expectedHorse.getAsDouble(), winner.getDistance());


    }

    private List<Horse> getRandomHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= HORSES_LIMIT; i++) {
            String name = "Horse" + i;
            double speed = (Math.random() + 0.1) * 11;
            double distance = (Math.random() + 0.1) * 21;
            horses.add(new Horse(name, speed, distance));
        }

        return horses;
    }


}
