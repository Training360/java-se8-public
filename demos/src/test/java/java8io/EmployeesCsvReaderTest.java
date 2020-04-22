package java8io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeesCsvReaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private EmployeesCsvReader employeesCsvReader = new EmployeesCsvReader();

    @Test
    public void testNumberOfCsvFiles() throws IOException {
        temporaryFolder.newFile("foo.csv");
        temporaryFolder.newFile("bar.csv");
        temporaryFolder.newFile("not_a_csv.txt");
        temporaryFolder.newFile("another_not_a_csv.docx");
        long number = employeesCsvReader.numberOfCsvFiles(temporaryFolder.getRoot().toPath());
        assertEquals(2, number);
    }

    @Test
    public void testLargestCsvFiles() throws IOException {
        temporaryFolder.newFolder("dir1");
        File f = temporaryFolder.newFile("dir1/foo.csv");
        Files.write(f.toPath(), "aaa".getBytes(StandardCharsets.UTF_8));
        File f2 = temporaryFolder.newFile("dir1/bar.csv");
        Files.write(f2.toPath(), "bbbbbb".getBytes(StandardCharsets.UTF_8));
        temporaryFolder.newFile("dir1/not_a_csv.txt");
        temporaryFolder.newFile("dir1/another_not_a_csv.docx");

        temporaryFolder.newFolder("dir2");
        File f3 = temporaryFolder.newFile("dir2/foo.csv");
        Files.write(f3.toPath(), "ccc".getBytes(StandardCharsets.UTF_8));
        temporaryFolder.newFile("dir2/bar.csv");
        File f4 = temporaryFolder.newFile("dir2/not_a_csv.txt");
        Files.write(f4.toPath(), "ddddddddd".getBytes(StandardCharsets.UTF_8));
        temporaryFolder.newFile("dir2/another_not_a_csv.docx");

        Path largest = employeesCsvReader.largestCsvFile(temporaryFolder.getRoot().toPath());
        assertTrue(largest.endsWith("dir1/bar.csv"));
    }

    @Test
    public void testLargestCsvFilesWithEmptyDir() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Contains no CSV file");

        employeesCsvReader.largestCsvFile(temporaryFolder.getRoot().toPath());
    }

    @Test
    public void testReadEmployees() throws IOException {
        File f = temporaryFolder.newFile();
        Files.write(f.toPath(),
                ("1,John Doe,180000\n" +
                "2,Jane Doe,200000")
                        .getBytes(StandardCharsets.UTF_8));

        List<Employee> employees = employeesCsvReader.readEmployees(f.toPath());
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
