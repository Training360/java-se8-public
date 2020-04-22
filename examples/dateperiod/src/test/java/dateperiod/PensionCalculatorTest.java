package dateperiod;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PensionCalculatorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSumEmploymentPeriods() {
        //Given
        PensionCalculator pc = new PensionCalculator();
        pc.addEmploymentPeriod(Period.parse("p2y2m4d"));
        pc.addEmploymentPeriod(Period.of(1, 12, 3));

        //Then
        assertThat(pc.sumEmploymentPeriods().getYears(), equalTo(4));
        assertThat(pc.sumEmploymentPeriods().getMonths(), equalTo(2));
        assertThat(pc.sumEmploymentPeriods().getDays(), equalTo(7));
        assertThat(pc.sumEmploymentPeriods().toString(), equalTo("P4Y2M7D"));
    }

    @Test
    public void testModifyByDays() {
        // Given
        Period period = Period.of(1, 2, 10);
        PensionCalculator pc = new PensionCalculator();

        //Then
        assertThat(pc.calculateTotalDays(pc.modifyByDays(period, 40)), equalTo(475));
        assertThat(pc.calculateTotalDays(pc.modifyByDays(period, -40)), equalTo(395));
    }

    @Test
    public void nullParameterShouldThrowException() throws NullPointerException {
        // Given
        exception.expect(NullPointerException.class);
        exception.expectMessage("Null parameters are not allowed!");
        // When
        new PensionCalculator().getPeriodBetweenDates(null, LocalDate.of(2005, 2, 5));
    }

    @Test
    public void testGetPeriodBetweenDates() {//
        //Given
        PensionCalculator pc = new PensionCalculator();
        //When
        Period period = pc.getPeriodBetweenDates(LocalDate.of(2000, 1, 3), LocalDate.of(2005, 2, 5));
        //Then
        assertThat(pc.calculateTotalDays(period), equalTo(1857));
    }

    @Test
    public void emptyFromDateParameterShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Empty from date string, cannot use");
        // When
        new PensionCalculator().getPeriodBetweenDates("", "2010-12-01", "yyyy-MM-dd");
    }

    @Test
    public void emptyToDateParameterShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Empty to date string, cannot use");
        // When
        new PensionCalculator().getPeriodBetweenDates("2010-12-01", "", "yyyy-MM-dd");
    }

    @Test
    public void emptyPatternParameterShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Empty pattern string, cannot use");
        // When
        new PensionCalculator().getPeriodBetweenDates("2010-12-01", "2012-04-05", "");
    }

    @Test
    public void testGetPeriodBetweenStringDates() {//
        //Given
        PensionCalculator pc = new PensionCalculator();
        //When
        Period period = pc.getPeriodBetweenDates("2010-12-01", "2012-12-01", "yyyy-MM-dd");
        //Then
        assertThat(pc.calculateTotalDays(period), equalTo(730));
    }
}
