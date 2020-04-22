package zonedaylight;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FlightInfoTest {

    public static final String PATTERN = "HHmm, dd MMM yyyy";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, Locale.ENGLISH);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void emptyDateStringShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Empty date string parameter!");
        // When
        new FlightInfo(null, PATTERN, Locale.ENGLISH, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
    }

    @Test
    public void emptyPatternStringShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Empty pattern string parameter!");
        // When
        new FlightInfo("xyz", "", Locale.ENGLISH, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
    }

    @Test
    public void nullLocaleShouldThrowException() throws NullPointerException {
        // Given
        exception.expect(NullPointerException.class);
        exception.expectMessage("Locale must not be null!");
        // When
        new FlightInfo("xyz", "xyz", null, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
    }

    @Test
    public void testGetDepartureDateTime() {
        //Given
        FlightInfo fi = new FlightInfo("1830, 14 Mar 2013", PATTERN, Locale.ENGLISH, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        //Then
        assertThat(FORMATTER.format(fi.getDepartureDateTime()), equalTo("1830, 14 Mar 2013"));
    }

    @Test
    public void testGetArrivalDateTimeWDifferentDSTPolicy() {
        //Given
        FlightInfo fi = new FlightInfo("1830, 14 Mar 2013", PATTERN, Locale.ENGLISH, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        //Then
        assertThat(FORMATTER.format(fi.getArrivalDateTime(ZoneId.of(ZoneId.SHORT_IDS.get("ECT")), 10, 30)), equalTo("1300, 15 Mar 2013"));
    }

    @Test
    public void testGetArrivalDateTime() {
        //Given
        FlightInfo fi = new FlightInfo("1830, 14 Apr 2013", PATTERN, Locale.ENGLISH, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        //Then
        assertThat(FORMATTER.format(fi.getArrivalDateTime(ZoneId.of(ZoneId.SHORT_IDS.get("ECT")), 10, 30)), equalTo("1400, 15 Apr 2013"));
    }
}
