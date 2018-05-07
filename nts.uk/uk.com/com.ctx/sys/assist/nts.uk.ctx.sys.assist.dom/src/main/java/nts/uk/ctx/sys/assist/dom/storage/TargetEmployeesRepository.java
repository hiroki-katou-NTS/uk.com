package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
 * データ保存の対象社員
 */
public interface TargetEmployeesRepository {

	List<TargetEmployees> getAllTargetEmployees();

	Optional<TargetEmployees> getTargetEmployeesById(String storeProcessingId, String employeeId);

	void add(TargetEmployees domain);

	void update(TargetEmployees domain);

	void remove(String storeProcessingId, String employeeId);

	/**
	 * @param employees
	 * @author nam.lh
	 */
	void addAll(List<TargetEmployees> employees);

	List<TargetEmployees> getTargetEmployeesListById(String storeProcessingId);
}
