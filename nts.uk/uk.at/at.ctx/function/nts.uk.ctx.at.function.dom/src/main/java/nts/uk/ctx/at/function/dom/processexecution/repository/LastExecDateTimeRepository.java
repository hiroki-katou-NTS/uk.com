package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;

public interface LastExecDateTimeRepository {
	public Optional<LastExecDateTime> get(String companyId, String execItemCd);
	public void insert(LastExecDateTime domain);
	public void update(LastExecDateTime domain);
}
