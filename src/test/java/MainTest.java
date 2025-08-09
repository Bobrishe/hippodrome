import org.junit.jupiter.api.*;

public class MainTest {
    @Disabled
    @Test
    @Timeout(22)
    public void shouldFinishedInSelectedTime() {
        try {
            String[] args = {};
            Main.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
