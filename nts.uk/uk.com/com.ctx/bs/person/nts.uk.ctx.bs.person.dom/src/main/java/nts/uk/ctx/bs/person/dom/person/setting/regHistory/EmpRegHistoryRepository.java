package nts.uk.ctx.bs.person.dom.person.setting.regHistory;

import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.setting.regHistory.EmpRegHistory;

public interface EmpRegHistoryRepository {

	Optional<EmpRegHistory> getLastRegHistory();

	void add(EmpRegHistory domain);

}
