package lambdaparallel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dates {

    public static void main(String[] args) {
        new Dates().generateDates();
    }

    private void generateDates() {
        long start = System.currentTimeMillis();
        List<String> dates = IntStream.range(0, 1_000_000)
                .parallel()
                .mapToObj(i -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .collect(Collectors.toList());
        System.out.println(dates.get(0));
        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
}
