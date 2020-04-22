package duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WorkHoursCalculatorTest {

    private WorkHoursCalculator whc;

    @Before
    public void setUp() {
        whc = new WorkHoursCalculator();
    }

    @After
    public void tearDown() {
        whc = null;
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testAddWorkTimeAsDuration() {
        //Given
        whc.addWorkTime(Duration.ofHours(6));
        //Then
        assertThat(whc.getWorkDuration().toMinutes(), equalTo(360L));
    }

    @Test
    public void testAddWorkTimeAsIntegers() {
        //When
        whc.addWorkTime(1, 2, 30);
        whc.addWorkTime(0, 2, 0);
        //Then
        assertThat(whc.getWorkDuration().toMinutes(), equalTo(1710L));
    }

    @Test
    public void emptyParameterShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Parameter must not be empty!");
        // When
        new WorkHoursCalculator().addWorkTime("");
    }

    @Test
    public void invalidParameterShouldThrowException() throws IllegalArgumentException {
        // Given
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Parameter must match PnDTnHnM pattern, but found:");
        // When
        new WorkHoursCalculator().addWorkTime("01:02:30");
    }

    @Test
    public void testAddWorkTimeAsString() {
        //When
        whc.addWorkTime("P1DT2H30M");
        whc.addWorkTime("P0DT2H0M");
        //Then
        assertThat(whc.getWorkDuration().toMinutes(), equalTo(1710L));
    }

    @Test
    public void testCalculateWorkHours() {
        //When
        whc.addWorkTime("P1DT2H30M");
        whc.addWorkTime("P0DT2H0M");
        //Then
        assertThat(whc.calculateWorkHours(), equalTo(28));
    }

    @Test
    public void testToString() {
        //When
        whc.addWorkTime("P1DT2H30M");
        //Then
        assertThat(whc.toString(), equalTo("Days: 1, hours: 2, minutes: 30"));
    }
}
