package java8io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeesCsvReader {

    public long numberOfCsvFiles(Path dir) {
        try (
                Stream<Path> entries = Files.list(dir)
                ) {
            return entries.filter(p -> p.toString().endsWith(".csv")).count();
        } catch (IOException ioe) {
            throw new IllegalStateException("Error reading directory", ioe);
        }
    }

    public Path largestCsvFile(Path dir) {
        try (
                Stream<Path> entries = Files.walk(dir)
                ) {
            return entries.filter(p -> p.toString().endsWith(".csv")).max(Comparator.comparing(EmployeesCsvReader::fileSize))
                    .orElseThrow(() -> new IllegalArgumentException("Contains no CSV file"));
        } catch (IOException ioe) {
            throw new IllegalStateException("Error reading directory", ioe);
        }
    }

    public List<Employee> readEmployees(Path path) {
        try (
            Stream<String> lines = Files.lines(path)
        ) {
            return lines.map(Employee::fromCsvLine).collect(Collectors.toList());
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Can not read file");
        }
    }

    public List<Employee> readDefaultEmployees() {
        try (
                Stream<String> lines = new BufferedReader(new InputStreamReader(EmployeesCsvReader.class.getResourceAsStream("employees.csv"))).lines()
        ) {
            return lines.skip(1).map(Employee::fromCsvLine).collect(Collectors.toList());
        }
    }

    private static long fileSize(Path p) {
        try {
            return Files.size(p);
        } catch (IOException e) {
            throw new IllegalStateException("Error getting files size", e);
        }
    }
}
