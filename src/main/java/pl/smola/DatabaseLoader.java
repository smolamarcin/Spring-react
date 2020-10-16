package pl.smola;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
final class DatabaseLoader implements CommandLineRunner{
    private final EmployeeRepository employeeRepository;

    @Autowired
    DatabaseLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.employeeRepository.save(new Employee("Frodo", "Baggins", "ring bearer"));
    }
}
