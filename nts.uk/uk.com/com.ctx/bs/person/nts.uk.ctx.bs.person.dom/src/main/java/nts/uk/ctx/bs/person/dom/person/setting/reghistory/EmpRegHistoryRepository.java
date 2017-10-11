package nts.uk.ctx.bs.person.dom.person.setting.reghistory;

import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;

public interface EmpRegHistoryRepository {

	Optional<EmpRegHistory> getLastRegHistory();

	void add(EmpRegHistory domain);

}
