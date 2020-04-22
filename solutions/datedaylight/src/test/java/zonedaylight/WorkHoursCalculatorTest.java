package zonedaylight;

import org.junit.Test;

import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WorkHoursCalculatorTest {

    public static final String PATTERN = "HHmm, dd MMM yyyy";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, Locale.ENGLISH);

    @Test
    public void testGetStartDateTime() {
        // When
        WorkHoursCalculator whc = new WorkHoursCalculator(2013, Month.MARCH, 5, 1, ZoneId.of("America/Chicago"));
        //Then
        assertThat(FORMATTER.format(whc.getStartDateTime()), equalTo("0100, 05 Mar 2013"));
    }

    @Test
    public void testCalculateHoursInMarch() {
        // Given
        Month month = Month.MARCH;
        // When/
        WorkHoursCalculator whc = new WorkHoursCalculator(2013, month, 1, 1, ZoneId.of("America/Chicago"));
        //Then
        assertThat(whc.calculateHours(2013, month, 16, 1), equalTo(359L));
    }

    @Test
    public void testCalculateHoursInApril() {
        // Given
        Month month = Month.APRIL;
        // When/
        WorkHoursCalculator whc = new WorkHoursCalculator(2013, month, 1, 1, ZoneId.of("America/Chicago"));
        //Then
        assertThat(whc.calculateHours(2013, month, 16, 1), equalTo(360L));
    }

    @Test
    public void testCalculateHoursInNovember() {
        // Given
        Month month = Month.NOVEMBER;
        // When/
        WorkHoursCalculator whc = new WorkHoursCalculator(2013, month, 1, 1, ZoneId.of("America/Chicago"));
        //Then
        assertThat(whc.calculateHours(2013, month, 16, 1), equalTo(361L));
    }

    @Test
    public void testCalculateHoursBetweenMonths() {
        // When/
        WorkHoursCalculator whc = new WorkHoursCalculator(2013, Month.MARCH, 1, 1, ZoneId.of("America/Chicago"));
        //Then
        assertThat(whc.calculateHours(2013, Month.APRIL, 11, 1), equalTo(983L));
    }
}
