package pl.smola;

import org.springframework.data.repository.CrudRepository;

/**
 * Dao layer to interact with {@link Employee} entities.
 * <p>Note: This interface has to be public to let Spring REST data create required endpoints.</p>
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
