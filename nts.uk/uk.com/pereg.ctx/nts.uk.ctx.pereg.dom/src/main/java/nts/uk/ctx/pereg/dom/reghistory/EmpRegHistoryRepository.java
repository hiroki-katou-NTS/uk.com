package nts.uk.ctx.pereg.dom.reghistory;

import java.util.Optional;

public interface EmpRegHistoryRepository {

	Optional<LastEmRegHistory> getLastRegHistory(String registeredEmployeeID, String companyId);

	Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID);

	void add(EmpRegHistory domain);

	void update(EmpRegHistory newEmpRegHistory);

	Optional<EmpRegHistory> getRegHistById(String employeeID);
}
