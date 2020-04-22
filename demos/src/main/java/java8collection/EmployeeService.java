package java8collection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    public List<Employee> removeWhereSalaryIsLowerThan(List<Employee> employees, int maxSalary) {
        employees.removeIf(e -> e.getSalary() < maxSalary);
        return employees;
    }

    public List<String> trimmedNames(List<String> names) {
        names.replaceAll(String::trim);
        return names;
    }

    public List<Employee> sortByName(List<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getName));
        return employees;
    }

    public List<Employee> convertNamesToLowerCase(List<Employee> employees) {
        employees.forEach(e -> e.setName(e.getName().toLowerCase()));
        return employees;
    }

    public Map<String, Employee> firstEmployeeWithFirstName(List<Employee> employees) {
        Map<String, Employee> firstEmployees = new HashMap<>();
        for (Employee employee: employees) {
            String firstName = employee.getName().split(" ")[0];
            firstEmployees.putIfAbsent(firstName, employee);
        }
        return firstEmployees;
    }

    public Map<String, Integer> countByFirstName(List<Employee> employees) {
        Map<String, Integer> counts = new HashMap<>();
        for (Employee employee: employees) {
            String firstName = employee.getName().split(" ")[0];
             counts.merge(firstName, 1, (i, j) -> i + j);
//            counts.compute(firstName, (k, v) -> v == null ? v = 1 : v + 1);
        }
        return counts;
    }

    public Map<Long, Employee> updateEmployees(Map<Long, Employee> base, List<Employee> changedEmployees) {
        for (Employee employee: changedEmployees) {
            base.replace(employee.getId(), employee);
        }
        return base;
    }

    public Map<Long, Integer> salariesChanged(Map<Long, Integer> base, List<Employee> changedEmployees) {
        for (Employee employee: changedEmployees) {
            base.remove(employee.getId(), employee.getSalary());
        }
        return base;
    }
}
