package java8collection;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EmployeeServiceTest {

    private EmployeeService employeeService = new EmployeeService();

    private List<Employee> employees = Collections.unmodifiableList(Arrays.asList(
            new Employee(1L, "John Doe", 180_000),
            new Employee(2L, "Jane Doe", 200_000),
            new Employee(3L,"Joe Doe", 100_000),
            new Employee(4L, "John Smith", 100_000)));

    @Test
    public void test_removeWhereSalaryIsLowerThan() {
        List<Employee> removed = employeeService.removeWhereSalaryIsLowerThan(new ArrayList<>(employees),
                190_000);
        assertEquals(Arrays.asList("Jane Doe"), removed.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void test_trimmedNames() {
        List<String> names = Arrays.asList("    John Doe    ", "   Jane Doe    ");
        List<String> replaced = employeeService.trimmedNames(names);
        assertEquals(Arrays.asList("John Doe", "Jane Doe"), replaced);
    }

    @Test
    public void test_sortByName() {
        List<Employee> sorted = employeeService.sortByName(new ArrayList<>(employees));
        assertEquals(Arrays.asList("Jane Doe", "Joe Doe", "John Doe", "John Smith"),
                sorted.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void test_convertNamesToLowerCase() {
        List<Employee> converted = employeeService.convertNamesToLowerCase(new ArrayList<>(employees));
        assertEquals(Arrays.asList("john doe", "jane doe", "joe doe", "john smith"),
                converted.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void test_countByFirstName() {
        Map<String, Integer> counts = employeeService.countByFirstName(new ArrayList<>(employees));
        assertEquals(Integer.valueOf(2), counts.get("John"));
        assertEquals(Integer.valueOf(1), counts.get("Jane"));
    }

    @Test
    public void test_firstEmployeeWithFirstName() {
        Map<String, Employee> names = employeeService.firstEmployeeWithFirstName(employees);
        assertEquals(3, names.size());
        assertEquals("John Doe", names.get("John").getName());
        assertEquals("Jane Doe", names.get("Jane").getName());
    }

    @Test
    public void test_updateEmployees() {
        List<Employee> changedEmployees = Arrays.asList(new Employee(1L, "John Doe", 250_000),
                new Employee(5L,"Jane Smith", 100_000));

        Map<Long, Employee> updated = employeeService.updateEmployees(new HashMap<>(employees.stream().collect(Collectors.toMap(Employee::getId, e -> e))),
                changedEmployees);

        assertEquals(4, updated.size());
        assertEquals(250_000, updated.get(1L).getSalary());
    }

    @Test
    public void test_salariesChanged() {
        List<Employee> changedEmployees = Arrays.asList(new Employee(1L, "John Doe", 180_001),
                new Employee(2L, "Jane Doe", 200_001),
                new Employee(3L,"Joe Doe", 100_000),
                new Employee(4L, "John Smith", 100_001));

        Map<Long, Integer> changed = employeeService.salariesChanged(
                employees.stream().collect(Collectors.toMap(Employee::getId, Employee::getSalary)),
                changedEmployees
        );

        assertEquals(3, changed.size());
        assertFalse(changed.containsKey(3L));
    }
}
