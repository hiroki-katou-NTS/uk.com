package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.Optional;
import java.util.List;

/**
 * データ削除の対象社員
 */
public interface EmployeesDeletionRepository {

	List<EmployeeDeletion> getAllEmployeesDeletion();

	Optional<EmployeeDeletion> getEmployeesDeletionById(String delId, String employeeId);

	void add(EmployeeDeletion domain);

	void update(EmployeeDeletion domain);

	void remove(String delId, String employeeId);

	/**
	 * @param employees
	 * @author hiep.th
	 */
	void addAll(List<EmployeeDeletion> employees);

	List<EmployeeDeletion> getEmployeesDeletionListById(String delId);
}
