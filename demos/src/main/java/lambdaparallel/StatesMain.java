package lambdaparallel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatesMain {

    public static void main(String[] args) {
        List<Integer> list = IntStream.range(0, 100_000)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println(list.size());
    }
}
