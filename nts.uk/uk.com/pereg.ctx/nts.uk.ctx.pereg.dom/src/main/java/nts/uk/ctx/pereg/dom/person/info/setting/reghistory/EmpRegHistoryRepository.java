package nts.uk.ctx.pereg.dom.person.info.setting.reghistory;

import java.util.Optional;

public interface EmpRegHistoryRepository {

	Optional<LastEmRegHistory> getLastRegHistory(String registeredEmployeeID, String companyId);

	Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID);

	void add(EmpRegHistory domain);

	void update(EmpRegHistory newEmpRegHistory);
}
