import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class HorseTest {

    private static final String HORSE_NAME = "Spirit";
    private static final double HORSE_SPEED = 10.0;
    private static final double HORSE_DISTANCE = 20.0;
    private static final double NEGATIVE_NUMBER = -5.0;
    private static final Horse HORSE_TWO_PARAMS = new Horse(HORSE_NAME, HORSE_SPEED);
    private static final Horse HORSE_THREE_PARAMS = new Horse(HORSE_NAME, HORSE_SPEED, HORSE_DISTANCE);

    private static final MockedStatic<Horse> MOCKED_STATIC = Mockito.mockStatic(Horse.class);


    @Test
    public void shouldThrowExceptionWhenFirstArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, HORSE_SPEED));
    }

    @Test
    public void shouldReturnCorrectMessageWhenFirstArgIsNull() {
        String expectedMessage = "Name cannot be null.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, HORSE_SPEED)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\r", "\f", "\t"})
    public void shouldThrowExceptionWhenFirstArgIsWhiteSpace(String firstArg) {

        assertThrows(IllegalArgumentException.class, () -> new Horse(firstArg, HORSE_SPEED));

    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\r", "\f", "\t"})
    public void shouldReturnCorrectMessageWhenFirstArgIsWhiteSpace(String firstArg) {

        String expectedMessage = "Name cannot be blank.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(firstArg, HORSE_SPEED));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSecondArgIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, NEGATIVE_NUMBER));
    }

    @Test
    public void shouldReturnCorrectMessageWhenSecondArgIsNegative() {
        String expecterMessage = "Speed cannot be negative.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, NEGATIVE_NUMBER));

        assertEquals(expecterMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenThirdArgIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, HORSE_SPEED, NEGATIVE_NUMBER));
    }

    @Test
    public void shouldReturnCorrectMessageWhenThirdArgIsNegative() {
        String expecterMessage = "Distance cannot be negative.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, HORSE_SPEED, NEGATIVE_NUMBER));

        assertEquals(expecterMessage, exception.getMessage());
    }

    @Test
    public void shouldMethodGetNameReturnCorrectName() {

        assertEquals(HORSE_NAME, HORSE_TWO_PARAMS.getName());

    }

    @Test
    public void shouldMethodGetSpeedReturnCorrectSpeed() {

        assertEquals(HORSE_SPEED, HORSE_TWO_PARAMS.getSpeed());

    }

    @Test
    public void shouldMethodGetDistanceReturnCorrectDistance() {

        assertEquals(HORSE_DISTANCE, HORSE_THREE_PARAMS.getDistance());

    }

    @Test
    public void shouldMethodGetDistanceReturnNullIfTwoArgs() {

        assertEquals(0, HORSE_TWO_PARAMS.getDistance());

    }

    @Test
    public void shouldCallStaticMethod() {

        Horse horse = new Horse(HORSE_NAME, HORSE_SPEED);
        horse.move();

        MOCKED_STATIC.verify(() -> Horse.getRandomDouble(0.2, 0.9));
    }

    @ParameterizedTest
    @CsvSource({"10,30,0.3", "15,55,0.4", "21,45,0.7", "23,56,0.8"})
    public void shouldReturnDistanceByCorrectFormula(double speed, double distance, double randomDouble) {

        double expectedDistance = distance + speed * randomDouble;

        MOCKED_STATIC.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);

        Horse horse = new Horse(HORSE_NAME, speed, distance);
        horse.move();

        assertEquals(expectedDistance, horse.getDistance());
    }


}
