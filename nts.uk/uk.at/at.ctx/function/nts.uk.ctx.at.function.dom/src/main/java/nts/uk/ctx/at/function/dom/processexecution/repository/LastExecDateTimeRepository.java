package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;

public interface LastExecDateTimeRepository {
	Optional<LastExecDateTime> get(String companyId, String execItemCd);
	void insert(LastExecDateTime domain);
	void update(LastExecDateTime domain);
	void remove(String companyId, String execItemCd);
}
