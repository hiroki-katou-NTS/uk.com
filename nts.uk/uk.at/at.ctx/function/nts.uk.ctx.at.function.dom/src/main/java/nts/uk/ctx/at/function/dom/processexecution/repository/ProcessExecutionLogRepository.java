package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;

public interface ProcessExecutionLogRepository {
	public List<ProcessExecutionLog> getProcessExecutionLogByCompanyId(String companyId);
	public Optional<ProcessExecutionLog> getLogByCIdAndExecCd(String companyId, String execItemCd, String execId);
	public void insert(ProcessExecutionLog domain);
	public void update(ProcessExecutionLog domain);
	public void remove(String companyId, String execItemCd, String execId);
	public void removeList(String companyId, String execItemCd);
}
