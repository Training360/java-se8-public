package lambdaparallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Urls {

    public static void main(String[] args)  throws Exception {
        new Urls().findLongWords();
    }

    public void findLongWords() throws Exception {
        String html = fetchUrl("https://www.index.hu");
        List<String> links = collectUrls(html);

        // URL-ek kiszűrése, hogy https://dex.hu címmel kezdődjön
        // Ne legyen duplikáció
        // Max. 10 db
        // URL-ek letöltése
        // Szavak kiválogatása
        // 12-nél hosszabbak leszűrése
        // Szavak, első

        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool(4);

        List<String> words = pool.submit(() ->
        links.parallelStream()
                .filter(s -> s.startsWith("https://dex.hu"))
                .distinct()
                .limit(10)
                .peek(System.out::println)
                .map(s -> fetchUrl(s))
                .flatMap(s -> words(s).stream())
                .filter(s -> s.length() > 12)
                .collect(Collectors.toList())).get();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }

    private List<String> words(String content) {
        return Arrays.asList(content.split("[\\P{L}]+"));
    }

    private List<String> collectUrls(String html) {
        List<String> links = new ArrayList<>();
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(html);
        while (m.find()) {
            links.add(m.group(1));
        }
        return links;
    }

    private String fetchUrl(String url) {
        try {
            return new BufferedReader(new InputStreamReader(new URL(url).openStream()))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Can not fetch url: " + url, e);
        }
    }
}
