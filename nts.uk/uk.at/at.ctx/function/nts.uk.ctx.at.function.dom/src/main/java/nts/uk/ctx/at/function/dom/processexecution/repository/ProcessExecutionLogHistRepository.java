package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;

public interface ProcessExecutionLogHistRepository {
	public Optional<ProcessExecutionLogHistory> getByExecId(String companyId,String execItemCd, String execId);
	public void insert(ProcessExecutionLogHistory domain);
	public void update(ProcessExecutionLogHistory domain);
	public void remove(String companyId, String execItemCd);
		
}
