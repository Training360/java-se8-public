package dateduration;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DurationTest {

    @Test
    public void testDuration() {
        Duration duration = Duration.ofHours(2);
        assertThat(duration.toString(), equalTo("PT2H"));
    }

    @Test
    public void testBetween() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 1, 12, 18, 0);
        Duration duration = Duration.between(localDateTime, localDateTime1);
        assertThat(duration.toString(), equalTo("PT2H"));
    }

    @Test
    public void testParse() {
        Duration duration = Duration.parse("PT2H");
        assertThat(duration.toString(), equalTo("PT2H"));
    }

    @Test
    public void testPlus() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDateTime localDateTime1 = localDateTime.plus(Duration.ofHours(2));
        assertThat(localDateTime1, equalTo(LocalDateTime.of(2018, 1, 12, 18, 0)));

    }

    @Test
    public void testDurationPlus() {
        Duration duration = Duration.ofHours(2).plusMinutes(3);
        assertThat(duration, equalTo(Duration.ofHours(3).plusMinutes(3).plusHours(-1)));
    }

    @Test
    public void testNormalize() {
        Duration duration = Duration.ofMinutes(250);
        assertThat(duration.toString(), equalTo("PT4H10M"));
    }
}
