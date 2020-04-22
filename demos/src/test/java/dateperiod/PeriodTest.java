package dateperiod;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PeriodTest {

    @Test
    public void testPeriod() {
        Period period = Period.ofDays(5);
        assertThat(period.getDays(), equalTo(5));

        Period period1 = Period.of(1, 2, 3);
        assertThat(period1.getMonths(), equalTo(2));
    }

    @Test
    public void testParse() {
        String s = "P2Y3M";
        Period period = Period.parse(s);
        assertThat(period.getMonths(), equalTo(3));
    }

    @Test
    public void testBetween() {
        LocalDate localDate = LocalDate.of(2018, 1, 12);
        LocalDate localDate1 = LocalDate.of(2018, 1, 9);

        Period period = Period.between(localDate, localDate1);
        assertThat(period.getDays(), equalTo(-3));
    }

    @Test
    public void testNormalize() {
        Period period = Period.ofMonths(20).normalized();
        System.out.println(period);
    }

    @Test
    public void testPlus() {
        LocalDate localDate = LocalDate.of(2018, 1, 12);
        Period period = Period.ofDays(3);
        LocalDate localDate1 = localDate.plus(period);
        assertThat(localDate1.getDayOfMonth(), equalTo(15));
    }

    @Test
    public  void testPeriodPlus() {
        Period period = Period.ofDays(2);
        Period period1 = period.plusMonths(2);
        assertThat(period1.toString(), equalTo("P2M2D"));
    }
}
