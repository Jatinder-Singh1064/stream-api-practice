package com.jatinder.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamApiPracticeApplication {

	static List<Employee> employees = new ArrayList<>();
	static {
		employees.add(
				new Employee("Jatinder", "Singh", 5000.00, List.of("Project 1, Project 2")));
		employees.add(
				new Employee("David", "Miller", 6000.00, List.of("Project 1, Project 3")));
		employees.add(
				new Employee("Rohit", "Sharma", 5500.00, List.of("Project 3, Project 4")));
	}

	public static void main(String[] args) {
		//SpringApplication.run(StreamApiPracticeApplication.class, args);

		//Terminal Operation - foreach
		employees.stream()
				.forEach(emp -> System.out.println(emp));

		//Intermediate Operation
		//map
		//collect
		List<Employee> increasedSalEmps =
								employees.stream()
								.map(emp -> new Employee(
										emp.getFirstName(),
										emp.getLastName(),
										emp.getSalary() * 1.10,
										emp.getProjects()
								))
								.collect(Collectors.toList());
		System.out.println(increasedSalEmps);


		//filter - Collecting Data
		List<Employee> filteredEmps =
		employees.stream()
				.filter(emp -> emp.getSalary() > 5000.0)
					.map(emp -> new Employee(
							emp.getFirstName(),
							emp.getLastName(),
							emp.getSalary() * 1.10,
							emp.getProjects()
					))
				.collect(Collectors.toList());
		System.out.println(filteredEmps);

		//Terminal Operation - findFirst
		Employee firstEmp =
				employees.stream()
						.filter(emp -> emp.getSalary() > 5000)
						.map(emp -> new Employee(
								emp.getFirstName(),
								emp.getLastName(),
								emp.getSalary() * 1.10,
								emp.getProjects()
						))
								.findFirst()
										.orElse(null);
		System.out.println(firstEmp);

		//flatMap - Intermediate Operation
		String projects =
						employees.stream()
						.map(employee -> employee.getProjects())
						.flatMap(strings -> strings.stream())
						.collect(Collectors.joining(","));
		System.out.println(projects);

		//Short Circuit operations
		//skip
		//limit
		List<Employee> skippedEmps =
				employees
						.stream()
							.skip(1)
							.limit(1)
							.collect(Collectors.toList());
		System.out.println(skippedEmps);

		//Finite Data
		Stream.generate(Math::random)
				.limit(5)
				.forEach(System.out::println);

		//Sorting
		List<Employee> sortedFirstNameEmps =
		employees
				.stream()
				.sorted((o1, o2)-> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
				.collect(Collectors.toList());
		System.out.println(sortedFirstNameEmps);

		//Aggregation
		//min or max
		Employee maxSalEmp =
		employees
				.stream()
				.max(Comparator.comparing(Employee::getSalary))
				.orElseThrow(NoSuchElementException::new);
		System.out.println(maxSalEmp);

		//Accumulation
		//reduce
		Double totalSalary =
		employees
				.stream()
				.map(employee -> employee.getSalary())
				.reduce(0.0, Double::sum);
		System.out.println(totalSalary);

		Double minSalary =
				employees
						.stream()
						.map(employee -> employee.getSalary())
						.reduce(0.0, Double::min);
		System.out.println(minSalary);

	}

}
