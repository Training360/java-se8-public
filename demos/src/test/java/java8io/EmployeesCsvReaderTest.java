package java8io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeesCsvReaderTest {

    @TempDir
    Path tempDir;

    private EmployeesCsvReader employeesCsvReader = new EmployeesCsvReader();

    @Test
    public void testNumberOfCsvFiles() throws IOException {
        Files.createFile(tempDir.resolve("foo.csv"));
        Files.createFile(tempDir.resolve("bar.csv"));
        Files.createFile(tempDir.resolve("not_a_csv.txt"));
        Files.createFile(tempDir.resolve("another_not_a_csv.docx"));
        long number = employeesCsvReader.numberOfCsvFiles(tempDir);
        assertEquals(2, number);
    }

    @Test
    public void testLargestCsvFiles() throws IOException {
        Files.createDirectory(tempDir.resolve("dir1"));
        Files.write(tempDir.resolve("dir1/foo.csv"), "aaa".getBytes(StandardCharsets.UTF_8));
        Files.write(tempDir.resolve("dir1/bar.csv"), "bbbbbb".getBytes(StandardCharsets.UTF_8));
        Files.createFile(tempDir.resolve("dir1/not_a_csv.txt"));
        Files.createFile(tempDir.resolve("dir1/another_not_a_csv.docx"));

        Files.createDirectory(tempDir.resolve("dir2"));
        Files.write(tempDir.resolve("dir2/foo.csv"), "ccc".getBytes(StandardCharsets.UTF_8));
        Files.write(tempDir.resolve("dir2/not_a_csv.txt"), "ddddddddd".getBytes(StandardCharsets.UTF_8));
        Files.createFile(tempDir.resolve("dir2/another_not_a_csv.docx"));

        Path largest = employeesCsvReader.largestCsvFile(tempDir);
        assertTrue(largest.endsWith("dir1/bar.csv"));
    }

    @Test
    public void testLargestCsvFilesWithEmptyDir() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            employeesCsvReader.largestCsvFile(tempDir);
        });
        assertEquals("Contains no CSV file", e.getMessage());
    }

    @Test
    public void testReadEmployees() throws IOException {
        Path file = tempDir.resolve("employees.csv");
        Files.write(file, ("1,John Doe,180000\n" +
                "2,Jane Doe,200000").getBytes(StandardCharsets.UTF_8));

        List<Employee> employees = employeesCsvReader.readEmployees(file);
        assertEquals(Arrays.asList("John Doe", "Jane Doe"),
                employees.stream().map(Employee::getName).collect(Collectors.toList()));
        assertEquals(Arrays.asList(180_000, 200_000),
                employees.stream().map(Employee::getSalary).collect(Collectors.toList()));
    }

    @Test
    public void testReadDefaultEmployees() {
        List<Employee> employees = employeesCsvReader.readDefaultEmployees();
        assertEquals(Arrays.asList("John Doe", "Jane Doe", "Joe Doe", "John Smith"),
                employees.stream().map(Employee::getName).collect(Collectors.toList()));
    }

}
