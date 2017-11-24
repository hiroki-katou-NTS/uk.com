package nts.uk.ctx.bs.person.dom.person.info.setting.reghistory;

import java.util.Optional;

public interface EmpRegHistoryRepository {

	Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID);

	void add(EmpRegHistory domain);

	void update(EmpRegHistory newEmpRegHistory);
}
